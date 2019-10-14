package personal.xiaoq.utils;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tanglu
 * create on 2019-3-29
 * @Description 身份证号码工具类
 */
public class IDCardUtil {

    private final static String[] specialTownCode = {"419001","429004","429005","429006","469001","469002","469003","469004","469005","469006","469007","659001","659002","659003","659004","659005","659006","659007"};
    private final static String[] specialProvinceCode = {"50","71","81","82","91","11","12","31"};

    /**
     *身份证验证
     * @param idStr
     * @return
     */
    public static boolean IdentityCardVerification(String idStr){
        String[] wf = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
        String[] checkCode = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
        String iDCardNo = "";
        try {
            //判断号码的长度 15位或18位
            if (idStr.length() != 15 && idStr.length() != 18) {
                return false;
            }
            if (idStr.length() == 18) {
                iDCardNo = idStr.substring(0, 17);
            } else if (idStr.length() == 15) {
                iDCardNo = idStr.substring(0, 6) + "19" + idStr.substring(6, 15);
            }
            if (isStrNum(iDCardNo) == false) {
                return false;
            }
            //判断出生年月
            String strYear = iDCardNo.substring(6, 10);// 年份
            String strMonth = iDCardNo.substring(10, 12);// 月份
            String strDay = iDCardNo.substring(12, 14);// 月份
            if (isStrDate(strYear + "-" + strMonth + "-" + strDay) == false) {
                return false;
            }
            GregorianCalendar gc = new GregorianCalendar();
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150 || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                return false;
            }
            if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
                return false;
            }
            if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
                return false;
            }
            //判断地区码
            Hashtable h = GetAreaCode();
            if (h.get(iDCardNo.substring(0, 2)) == null) {
                return false;
            }
            //判断最后一位
            int theLastOne = 0;
            for (int i = 0; i < 17; i++) {
                theLastOne = theLastOne + Integer.parseInt(String.valueOf(iDCardNo.charAt(i))) * Integer.parseInt(checkCode[i]);
            }
            int modValue = theLastOne % 11;
            String strVerifyCode = wf[modValue];
            iDCardNo = iDCardNo + strVerifyCode;
            if (idStr.length() == 18 &&!iDCardNo.equalsIgnoreCase(idStr)) {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 地区代码
     * @return Hashtable
     */
    public static Hashtable<String,String> GetAreaCode() {
        Hashtable<String,String> hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }


    /**
     * 港澳台 身份证 验证
     * @param idCard
     * @return
     */
    public static boolean validateHkMcTwIdCard(String idCard){
        String card = idCard.replaceAll("[\\(|\\)]", "");
        if (card.length() != 8 && card.length() != 9 && idCard.length() != 10) {
            return false;
        }
        if (idCard.matches("^[a-zA-Z][0-9]{9}$")) { // 台湾
            return true;
        }else if (idCard.matches("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?$")) { // 澳门
            return true;
        }else if (idCard.matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A-Z]\\)?$")) { // 香港
            return true;
        }
        return false;
    }


    /**
     * 判断城市编号 是否为 省级直辖县行政区
     * @param cityCode 大陆身份证前六位
     * @return
     */
    public static boolean isSpecialTown(String cityCode){
        List<String> list = Arrays.asList(specialTownCode);
        return list.contains(cityCode);
    }

    /**
     * 判断城市编号 是否为  直辖市或特别行政区
     * @param cityCode 大陆身份证前六位
     * @return
     */
    public static boolean isSpecialProvince(String cityCode){
        List<String> list = Arrays.asList(specialProvinceCode);
        return list.contains(cityCode);
    }


    /**
     * 港澳台 身份证 验证
     * @param idCard
     * @return
     */
    public static String getHkMcTwIdCardCode(String idCard){
        String card = idCard.replaceAll("[\\(|\\)]", "");
        if (card.length() != 8 && card.length() != 9 && idCard.length() != 10) {
            return "";
        }
        if (idCard.matches("^[a-zA-Z][0-9]{9}$")) { // 台湾
            return "71";
        }else if (idCard.matches("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?$")) { // 澳门
            return "82";
        }else if (idCard.matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A-Z]\\)?$")) { // 香港
            return "81";
        }
        return "";
    }



    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    private static boolean isStrNum(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 判断字符串是否为日期格式
     * @param strDate
     * @return
     */
    private static boolean isStrDate(String strDate) {
        Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        if(args.length<3 || args[0]==null || args[1]==null){
            System.out.println("请输入参数");
            return ;
        }
        System.out.printf("输入文件路径，输入%s,输出%s",args[0],args[1]);

        StringBuffer sbf = new StringBuffer();

        try (FileReader reader = new FileReader(args[0]);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                String[] dataArr = line.replaceAll("\"","").split(",");
                //1、判断一行记录 是否携带正确的身份证
                String idCard = "";
                boolean f =false;
                for(int i = 0; i < dataArr.length; i++){
                    f = IDCardUtil.IdentityCardVerification(dataArr[i]);
                    if(!f){
                        f = IDCardUtil.validateHkMcTwIdCard(dataArr[i]);
                        if(f){
                            idCard=dataArr[i];
                            i = dataArr.length;
                        }
                    }else{
                        idCard=dataArr[i];
                        i = dataArr.length;
                    }
                }
                if(!f){ //如果不是携带正确身份证的数据就丢掉
                    System.out.println(line+"身份证校验错误");
                    continue;
                }
                //2、将正确的身份证号转换成城市编码
                if("0".equals(args[2])){   //0表示市
                    String townCode;
                    if(IDCardUtil.validateHkMcTwIdCard(idCard)){   //如果是港澳台身份证
                        townCode = IDCardUtil.getHkMcTwIdCardCode(idCard)+"0000";
                    }else{
                        if(IDCardUtil.isSpecialTown(idCard.substring(0,6))){  //判断是否为 省级直辖县行政区
                            townCode = idCard.substring(0,6);  //如果是 直接截取身份证前6位
                        }else if(IDCardUtil.isSpecialProvince(idCard.substring(0,2))){//判断是否为 直辖市或特别行政区
                            townCode = idCard.substring(0,2)+"0000";  //如果是 直接截取身份证前6位
                        }else{
                            townCode = idCard.substring(0,4)+"00";
                        }
                    }

                    String result = "";
                    for (String s : dataArr){
                        result = result+s+"\t";
                    }
                    result=result+townCode+"\n";
                    sbf.append(result);

                }else if("1".equals(args[2])) {  //省
                    String provinceCode ;
                    if(IDCardUtil.validateHkMcTwIdCard(idCard)){   //如果是港澳台身份证
                        provinceCode = IDCardUtil.getHkMcTwIdCardCode(idCard)+"0000";
                    }else{
                        provinceCode = idCard.substring(0,2)+"0000"; //如果是大陆身份证 就取前两位
                    }
                    String result = "";
                    for (String s : dataArr){
                        result = result+s+"\t";
                    }
                    result=result+provinceCode+"\n";
                    sbf.append(result);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(sbf.toString());


        try {
            File writeName = new File(args[1]); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(sbf.toString()); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
