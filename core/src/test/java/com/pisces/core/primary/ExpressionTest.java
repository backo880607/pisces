package com.pisces.core.primary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pisces.core.AppTest;
import com.pisces.core.utils.IExpression;
import com.pisces.core.utils.Primary;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTest.class)
public class ExpressionTest {
    
	@Test
	public void testAdd() {
		IExpression expression = Primary.get().createExpression("1+2");
		if (expression != null) {
			Object value = expression.getValue();
			System.out.println(value);
		}
	}
}
