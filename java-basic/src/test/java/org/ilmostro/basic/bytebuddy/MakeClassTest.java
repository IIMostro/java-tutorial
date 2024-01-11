package org.ilmostro.basic.bytebuddy;

import com.alibaba.fastjson.JSON;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.FieldAccessor;
import org.ilmostro.basic.SimpleUser;
import org.junit.jupiter.api.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class MakeClassTest {

  @Test
  public void subclass() throws Exception {
    final Class<? extends SimpleUser> userClass =
        new ByteBuddy()
            .subclass(SimpleUser.class)
            .defineField("password", String.class, Visibility.PRIVATE)
            .implement(PasswordInterface.class)
            .intercept(FieldAccessor.ofBeanProperty())
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();

    final SimpleUser simpleUser = userClass.newInstance();

    final Method method = userClass.getMethod("setPassword", String.class);
    method.invoke(simpleUser, "123456");
    log.info(JSON.toJSONString(simpleUser, true));
  }

  public interface PasswordInterface {

    String getPassword();

    void setPassword(String password);
  }
}
