package server;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    public static Map<String, String> parseUrlEncoded(String  rawLines, String delimiter){
        //разделим rawLines(преобразованное тело запроса) по делимитру и запишем в массив
        String[] pairs = rawLines.split(delimiter);
        //запускаем поток обработки из Arrays поместив туда массив pairs
        Stream<Map.Entry<String, String>> stream = Arrays.stream(pairs)
                //преобразуем
                .map(Utils::decode)
                //отфильтруем те, которые есть
                .filter(Optional::isPresent)

                .map(Optional::get);
        return stream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    };

    private  static  Optional<Map.Entry<String, String>> decode(String kv){
        if(!kv.contains("=")){
            return Optional.empty();
        }
        String[] pair = kv.split("=");
        // если в элементе нет символа = то
        // это не то что нам требуется
        if(pair.length != 2){
            return Optional.empty();
        }
        Charset utf8 = StandardCharsets.UTF_8;
        String key = URLDecoder.decode(pair[0], utf8);
        String value = URLDecoder.decode(pair[1], utf8);

        return Optional.of(Map.entry(key,value));
    }
}