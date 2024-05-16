package daeucna.config.security.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class ExceptionController {

    @Operation(summary = "인가 거부", description = "인가에 따른 예외가 발생했습니다.")
    @RequestMapping(value = "/exceptionDenied", method = {RequestMethod.GET, RequestMethod.POST})
    public void AccessDeniedException() {
        throw new AccessDeniedException("인가거부");
    }
        
}
