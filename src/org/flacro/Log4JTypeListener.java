package org.flacro;

import java.lang.reflect.Field;
import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.MembersInjector;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

class Log4JMembersInjector<T> implements MembersInjector<T> {
    private final Field field;
    private final Logger logger;

    Log4JMembersInjector(Field field) {
      this.field = field;
      this.logger = Logger.getLogger(field.getDeclaringClass());
      field.setAccessible(true);
    }

    public void injectMembers(T t) {
      try {
        field.set(t, logger);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }

public class Log4JTypeListener implements TypeListener {
    public <T> void hear(TypeLiteral<T> typeLiteral, TypeEncounter<T> typeEncounter) {
      for (Field field : typeLiteral.getRawType().getDeclaredFields()) {
        if (field.getType() == Logger.class
            && field.isAnnotationPresent(InjectLogger.class)) {
          typeEncounter.register(new Log4JMembersInjector<T>(field));
        }
      }
    }
  }