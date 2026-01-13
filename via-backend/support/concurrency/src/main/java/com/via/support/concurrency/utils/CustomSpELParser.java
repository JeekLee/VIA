package com.via.support.concurrency.utils;

import lombok.NoArgsConstructor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@NoArgsConstructor
public class CustomSpELParser {
    private static final ExpressionParser parser = new SpelExpressionParser();

    public static Object parse(String[] parameterNames, Object[] args, String spEL) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) context.setVariable(parameterNames[i], args[i]);
        return parser.parseExpression(spEL).getValue(context, Object.class);
    }
}
