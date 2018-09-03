import com.elevenst.TerroirApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 *  xml파일을 읽어들여, <insert>, <update>, <delete> 태그들을 불러서 인덱싱 해 주는 유틸리티
 *
 * */
public class IndexingIDUQuery {

    public static void main(String[] args) {

        //ClassLoader.getSystemResourceAsStream()
        //Thread.currentThread().getContextClassLoader().getResourceAsStream();

        //ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:com/elevenst/terroir/product/registration/**/mapper/*.xml");

//        ClassLoader cl = ClassLoader.getSystemClassLoader();
//        System.out.println(IndexingIDUQuery.class.getResource("/"));
//        System.out.println();


    }

}
