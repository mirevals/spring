package security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleCheckAspect {

    @Before("@annotation(security.RequiresTeacher)")
    public void checkTeacherRole() {
        if (RoleContext.getRole() != Role.TEACHER) {
            throw new SecurityException("Only teachers can perform this action.");
        }
    }
}
