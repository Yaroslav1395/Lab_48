package server;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public class Cookie<V> {
  private final String name;
  private final V value;
  private Integer maxAge;
  private boolean httpOnly;


  public Cookie(String name, V value) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(value);
    this.name = name.strip();
    this.value = value;
  }


  public static <V> Cookie make(String name, V value) {
    return new Cookie<>(name, value);
  }

  public void setMaxAge(Integer maxAgeInSeconds) {
    this.maxAge = maxAgeInSeconds;
  }

  public void setHttpOnly(boolean httpOnly) {
    this.httpOnly = httpOnly;
  }

  private V getValue() { return value; }
  private Integer getMaxAge() { return maxAge; }
  private String getName() { return name; }
  private boolean isHttpOnly() { return httpOnly; }

  public  static Map<String, String> parse(String cookieString){
    return Utils.parseUrlEncoded(cookieString, "; ");
  }

  @Override
  public String toString() {
    //Это mutable класс, т.е. изменяемый. Объект класса StringBuilder
    // может содержать в себе определенный набор символов, длину и
    // значение которого можно изменить через вызов определенных методов
    StringBuilder sb = new StringBuilder();
    //установим кодировку
    Charset utf8 = StandardCharsets.UTF_8;
    //декодируем имя
    String encName = URLEncoder.encode(getName().strip(), utf8);
    //получаем значение и переводим в строку
    String stringValue = getValue().toString();
    //декодируем значение
    String encValue = URLEncoder.encode(stringValue.strip(), utf8);
    sb.append(String.format("%s=%s", encName, encValue));
    //проверка куки на длительность существование
    if (getMaxAge() != null) {
      sb.append(String.format("; Max-Age=%s", getMaxAge()));
    }
    //если куки нет, то скажем HttpOnly
    if (isHttpOnly()) {
      sb.append("; HttpOnly");
    }

    return sb.toString();
  }
}