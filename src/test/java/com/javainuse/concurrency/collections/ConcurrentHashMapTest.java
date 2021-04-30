package com.javainuse.concurrency.collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConcurrentHashMapTest {

    @Test
    public void givenHashMap_whenSumParallel_thenError() throws Exception {
        Map<String, Integer> map = new HashMap<>();
        List<Integer> sumList = parallelSum100(map, 100);

        assertNotEquals(1, sumList
                .stream()
                .distinct()
                .count());
        long wrongResultCount = sumList
                .stream()
                .filter(num -> num != 100)
                .count();

        assertTrue(wrongResultCount > 0);
    }

    @Test
    public void givenConcurrentMap_whenSumParallel_thenCorrect()
            throws Exception {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        List<Integer> sumList = parallelSum100(map, 1000);

        assertEquals(1, sumList
                .stream()
                .distinct()
                .count());
        long wrongResultCount = sumList
                .stream()
                .filter(num -> num != 100)
                .count();

        assertEquals(0, wrongResultCount);
    }

    @Test
    public void givenMaps_whenGetPut500KTimes_thenConcurrentMapFaster() throws Exception {
        Map<String, Object> hashtable = new Hashtable<>();
        Map<String, Object> synchronizedHashMap = Collections.synchronizedMap(new HashMap<>());
        Map<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

        long hashtableAvgRuntime = timeElapseForGetPut(hashtable);
        long syncHashMapAvgRuntime = timeElapseForGetPut(synchronizedHashMap);
        long concurrentHashMapAvgRuntime = timeElapseForGetPut(concurrentHashMap);

        assertTrue(hashtableAvgRuntime > concurrentHashMapAvgRuntime);
        assertTrue(syncHashMapAvgRuntime > concurrentHashMapAvgRuntime);
    }

    private long timeElapseForGetPut(Map<String, Object> map) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        long startTime = System.nanoTime();
        for (int i = 0; i < 4; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 500_000; j++) {
                    int value = ThreadLocalRandom.current().nextInt(10000);
                    String key = String.valueOf(value);
                    map.put(key, value);
                    map.get(key);
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        return (System.nanoTime() - startTime) / 500_000;
    }

//    @Test
//    public void givenConcurrentMap_whenUpdatingAndGetSize_thenError() throws InterruptedException {
//
//        Runnable collectMapSizes = () -> {
//            for (int i = 0; i < MAX_SIZE; i++) {
//                mapSizes.add(concurrentMap.size());
//            }
//        };
//        Runnable updateMapData = () -> {
//            for (int i = 0; i < MAX_SIZE; i++) {
//                concurrentMap.put(String.valueOf(i), i);
//            }
//        };
//        executorService.execute(updateMapData);
//        executorService.execute(collectMapSizes);
//        executorService.shutdown();
//        executorService.awaitTermination(1, TimeUnit.MINUTES);
//
//        assertNotEquals(MAX_SIZE, mapSizes.get(MAX_SIZE - 1).intValue());
//        assertEquals(MAX_SIZE, concurrentMap.size());
//    }

    private List<Integer> parallelSum100(Map<String, Integer> map, int executionTimes) throws InterruptedException {
        // 1000 values!
        List<Integer> sumList = new ArrayList<>(1000);
        // 100 times we execute
        for (int i = 0; i < executionTimes; i++) {
            map.put("test", 0);
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            for (int j = 0; j < 10; j++) {
                executorService.execute(() -> {
                    for (int k = 0; k < 10; k++)
                        map.computeIfPresent("test", (key, value) -> value + 1);
                });
            }
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
            sumList.add(map.get("test"));
        }
        return sumList;
    }

}
