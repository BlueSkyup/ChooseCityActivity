# ChooseCityActivity
地址选择Activity
![image](https://github.com/BlueSkyup/ChooseCityActivity/blob/master/%E6%95%88%E6%9E%9C%E5%9B%BE.png)

第一步  将上述文件复制到 项目中   其中  layout_drawer_city_choose.xml 中的  MyScrollView 等几个自定义控件的名字需要改名  

第二步  在ChooseCityActivity 中的 initData  方法中  写入 hotcity 和  all city 数组   

第三步  前一个Activity  中  startActivityForResult 中  传入 private static final String CHOOSE_CITY_FLAG = "choose_city";  标志位  

第四步  在  前一个Activity 中  onActivityResult 方法中  取得返回的地区名  即可
