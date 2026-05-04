package com.helloword.loteria.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
cada vez que se añada un servicio nuevo en el futuro
se guarde un log de auditoría sin tener que escribir código extra.
 */
@Aspect
@Component
public class ServiceAuditAspect {
    /*
    El usuario llama a un endpoint (ej. POST /users/USR001/bets).
    Spring busca el método en el LotteryService.
    Mi aspecto salta antes que lo de juan se ejecute, y registra el acceso al servicio y método.
    Se escribe en audit.log
    El servicio de tu compañero se ejecuta normalmente.
     */

    // Apuntamos al logger específico que creamos en el XML
    private static final Logger auditLog = LoggerFactory.getLogger("AuditLogger");

    //En este ejemplo, se interceptan todos los métodos dentro de cualquier clase en el paquete "services"
    @Before("execution(* com.helloword.loteria..services..*(..))")
    public void auditServiceCall(JoinPoint joinPoint) {
        String serviceName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        // Esto irá directamente a audit.log
        auditLog.info("Acceso al servicio: {} -> Método: {}", serviceName, methodName);
    }
}