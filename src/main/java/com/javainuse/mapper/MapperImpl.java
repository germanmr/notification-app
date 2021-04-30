package com.javainuse.mapper;

import com.javainuse.dto.Square;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.Set;

@Service
public class MapperImpl {

//    @Autowired
//    @Qualifier("defaultValidator")
//    private final Validator validator;
//
//    @Inject
//    public MapperImpl(Validator validator) {
//        this.validator = validator;
//    }

    public Square validateInputWithInjectedValidator(Square square) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Square>> violations = validator.validate(square);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return square;
    }
}
