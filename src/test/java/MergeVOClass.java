import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 두개의 VO를 머지시켜주는 유틸리티 클래스
 *
 *  주의사항 :
 *          되도록이면 합칠 기준이 되는 클래스를 첫번째 파라미터로 넣어주세요
 *          field Name이 같고 그 Type이 다른 경우에는 첫번째 파라미터의 Type으로 세팅할겁니다.
 */
public class MergeVOClass {
    public static void main(String[] args) {

        CategoryVO first = new CategoryVO();

        mergeObjects(CategoryVO.class, CategoryVO.class);
    }


    private static <T1,T2> void mergeObjects(Class<T1> first, Class<T2> second) {

        final String CLASS_DELIM = ".";


        Field[] firstFields = first.getDeclaredFields();
        Field[] secondFields = second.getDeclaredFields();

        Set<String> mergedSet = new HashSet<String>();

        Map<String, String> mergedMap = new HashMap<String, String>();

        for(Field elem1 : firstFields) {
            String type = elem1.getType().getName();
            String name = elem1.getName();
            String lastType = type.lastIndexOf(".") < 0 ? type : type.substring(type.lastIndexOf(".")+1, type.length());

            mergedMap.put(name, lastType);

//            String mergeElem = lastType + ";" + name;
//            mergedSet.add(mergeElem);
        }

        for(Field elem2 : secondFields) {
            String type = elem2.getType().getName();
            String name = elem2.getName();
            String lastType = type.lastIndexOf(".") < 0 ? type : type.substring(type.lastIndexOf(".")+1, type.length());

            if(StringUtils.isEmpty(mergedMap.get(name))) {
                mergedMap.put(name, lastType);
            }

//            String mergeElem = lastType + ";" + name;
//            mergedSet.add(mergeElem);
        }

        for (String key : mergedMap.keySet()) {

            String result = "private " + mergedMap.get(key) + " " + key + ";";
//            String result = "private " +
//            String result = "private " + elem.split(";")[0] + " " + elem.split(";")[1] + ";";
            System.out.println(result);
        }


    }
}
