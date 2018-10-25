import com.saber.utils.PinYin4jUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.StringUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Demo {
    @Test
    public void fun1() throws IOException {
        String filePath="C:\\Users\\pc\\Desktop\\Bos\\bos-parent1\\bos-web1\\src\\test\\java\\区域导入测试数据.xls";

        //包装一个Excel文件对象
        HSSFWorkbook workbook =new HSSFWorkbook(new FileInputStream(filePath));

        //读取文件中第一个Sheet标签页
        HSSFSheet sheetAt =workbook.getSheetAt(0);

        //遍历标签页中所有行
        for (Row row: sheetAt) {
            System.out.println();
            //第一行的数据不需要保存到数据中,理应跳过
            int rowNum =row.getRowNum();
            if (rowNum ==0){
                continue;
            }
            for(Cell cell:row){
                String value =cell.getStringCellValue();
                System.out.print(value+" ");
            }
        }
    }

    @Test
    public void fun2(){
        //河北省   石家庄市   桥西区
        String province = "河北省";
        String city = "石家庄市";
        String district = "桥西区";

        province =province.substring(0,province.length()-1);
        city =city.substring(0,city.length()-1);
        district = district.substring(0,district.length()-1);
        //简称：河北石家庄桥西
        String info =province+city+district;
        System.out.println(info);

        //简码：HBSJZQX
        String [] headByString = PinYin4jUtils.getHeadByString(info);
        String join = StringUtils.join(headByString);
        System.out.println(join);

        //城市编码  ---》》shijiazhuang  shijiazhuang
        String cityP = PinYin4jUtils.hanziToPinyin(city,"");
        System.out.println(cityP);
    }
}
