package com.dragon.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dragon.po.BmAll;
import com.dragon.po.Student;
import com.dragon.po.TestPo;
import com.dragon.service.BmAllService;
import com.dragon.utils.JsonResult;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import static com.sun.xml.internal.fastinfoset.util.DuplicateAttributeVerifier.MAP_SIZE;

/**
 * Created by Administrator on 2018/3/6.
 */
@Controller
@RequestMapping(value="/admin")
public class BmAllController {

    @Autowired
    private BmAllService bmAllService;

    @RequestMapping(value = "/bmAll", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserList (){
        JsonResult r = new JsonResult();
        try {
            List<BmAll> bmAllList = bmAllService.fetctBmAll();
            r.setResult(bmAllList);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    @RequestMapping(value="/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value="/lsbk")
    public String jbxx(@RequestBody TestPo po) {
        return "TRyLsbkJbxx";
    }


    @RequestMapping(value = "uploadFile2")
    //返回JSON格式必须有 @ResponseBody
    @ResponseBody
    public String uploadFile2(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, String> resObj = new HashMap<>();
        if (!file.isEmpty()) {
            //方式一 已过时
            // 解析每行结果在listener中处理
            /*AnalysisEventListener listener = new ExcelListener();
            ExcelReader excelReader = new ExcelReader(file.getInputStream(), ExcelTypeEnum.XLSX, null, listener);
            excelReader.read(new Sheet(1, 1, TestPo.class));*/

            List<Object> data = EasyExcelFactory.read(file.getInputStream(), new Sheet(1, 1,Student.class));
            for(Object object : data) {
                Student po = (Student) object;
                System.out.println(po.getSfzh());
            }
            return JSONUtils.toJSONString(resObj);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "downloadFileTwo")
    public String testDownload2(HttpServletResponse response, HttpServletRequest request) {
        //根据条件获取要下载的数据
        List<Student> stuList = bmAllService.fetchAllStu();

        //无模板
        try {
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS, true);
            Sheet sheet = new Sheet(1, 3, Student.class, "sheet", null);
            /*TableStyle style = new TableStyle();
            //...设置TableStyle
            sheet.setTableStyle(style);*/
            writer.write(stuList, sheet);
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=123.xls");
            writer.finish();
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* //有模板
        try {
            File xlsFile = new File("G://studentTest.xls");
            InputStream in = new FileInputStream(xlsFile);
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = EasyExcelFactory.getWriterWithTemp(in, out,ExcelTypeEnum.XLS,false);
            Sheet sheet = new Sheet(1, 3, Student.class, "sheet", null);
            writer.write(stuList, sheet);
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=studentTest.xls");
            writer.finish();
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    @RequestMapping(value = "uploadFile")
    //返回JSON格式必须有 @ResponseBody
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, String> resObj = new HashMap<>();
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            //获取项目根路径
            String path = request.getSession().getServletContext().getRealPath("upload");
            //即将上传到并生成的文件
            File tempFile = new File(path, new Date().getTime() + String.valueOf(fileName));
            //是否存在文件目录
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdir();
            }
            //是否存在文件
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }
            //保存文件
            file.transferTo(tempFile);

            //1.获取工作簿
            Workbook book = getWorkBook(file.getInputStream(), fileName);
//            Sheet sheet = book.getSheetAt(0);//获取第一张工作表
            //2.获取所有工作表
            List<org.apache.poi.ss.usermodel.Sheet> sheets = getSheets(book);
            //3.遍历每个单元格并保存到数据库
            putDataToDB(sheets);
            resObj.put("msg", "ok");
            resObj.put("code", "0");

            return JSONUtils.toJSONString(resObj);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "downloadFile")
    public String testDownload(HttpServletResponse response, HttpServletRequest request) {
        //根据条件获取要下载的数据
        List<Student> stuList = bmAllService.fetchAllStu();
        //创建工作簿对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表对象
        HSSFSheet sheet = workbook.createSheet();
        //设置工作表名称
        workbook.setSheetName(0, "test");
        //在工作表中创建行对象
        HSSFRow row = sheet.createRow(0);
        int x = 0;
        for(Student stu : stuList) {
            x++;
            HSSFRow dataRow = sheet.createRow(x);//创建行
            HSSFCell name = dataRow.createCell(0);//创建单元格
            name.setCellValue(stu.getName());//将数据添加到单元格
            HSSFCell sfzh = dataRow.createCell(1);//创建单元格
            sfzh.setCellValue(stu.getSfzh());//将数据添加到单元格
            HSSFCell xf = dataRow.createCell(2);//创建单元格
            xf.setCellValue(stu.getXf());//将数据添加到单元格
            HSSFCell zsf = dataRow.createCell(3);//创建单元格
            zsf.setCellValue(stu.getZsf());//将数据添加到单元格
        }
        //获取项目根路径
//        String path = request.getSession().getServletContext().getRealPath("upload");
        File xlsFile = new File("G://studentTest.xls");
        try {
            FileOutputStream fos = new FileOutputStream(xlsFile);
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=studentTest.xls");// 设置文件名
            //将工作簿直接写到response输出流
            workbook.write(response.getOutputStream());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //要下载的文件名
        /*String fileName = "ttt.xlsx";
        if (fileName != null) {
            //通过要下载的文件路径，创建file对象
            File file = new File("G://studentTest.xls");
            //File file = new File(realPath , fileName);
            if (file.exists()) {
                response.reset();
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";*/
        /*String fileName = "ttt.xlsx";
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File("G://"
                    + fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");*/
        return null;
}

    /**
     * 得到POI的工作簿
     * @param in
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Workbook getWorkBook(InputStream in,String path) throws FileNotFoundException, IOException {
        if(path.endsWith(".xls")) {
            return new HSSFWorkbook(in);
        } else if(path.endsWith(".xlsx")) {
            return new XSSFWorkbook(in);
        }
        return null;
    }

    /**
     * 获取Excel中的所有工作表
     * @param book
     * @return
     */
    private List<org.apache.poi.ss.usermodel.Sheet> getSheets(Workbook book) {
        int numberOfSheets = book.getNumberOfSheets();
        System.out.println("numberOfSheets:" + numberOfSheets);
        List<org.apache.poi.ss.usermodel.Sheet> sheets = new ArrayList<org.apache.poi.ss.usermodel.Sheet>();
        for (int i = 0; i < numberOfSheets; i++) {
            sheets.add(book.getSheetAt(i));
        }
        return sheets;
    }

    /**
     * 遍历每张工作表中的每行数据
     * @param sheets
     */
    private void putDataToDB(List<org.apache.poi.ss.usermodel.Sheet> sheets) {
        //循环每一张工作表
        for (int i = 0; i < sheets.size(); i++) {
            org.apache.poi.ss.usermodel.Sheet sheet = sheets.get(i);
            //判断是否为空表，获取有数据的最后一行的行数。如果为零则为空表
            if (sheet.getLastRowNum() > 1) {
                System.out.println(sheet.getSheetName() + "=============");
            }
            //获得每行的迭代器
            Iterator<Row> iterator = sheet.iterator();
            //遍历每一行
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                if (nextRow.getRowNum() < 1) {
                    continue;
                    //nextRow.getRowNum()就是获取行数，由表中看出第一行(getRowNum()=0)为表头，直接跳过
                }

                //从第二行开始是有用的数据，要保存早数据库，第二行：nextRow.getRowNum()=1
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                Student stu = new Student();
                //遍历每一行的每一列
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch(cell.getColumnIndex()){
                        case 0:
                            stu.setName(cell.getStringCellValue());
                            break;
                        case 1:
                            stu.setSfzh(String.valueOf((int)cell.getNumericCellValue()));
                            break;
                        case 2:
                            stu.setXf(String.valueOf((int)cell.getNumericCellValue()));
                            break;
                        case 3:
                            stu.setZsf(String.valueOf((int)cell.getNumericCellValue()));
                            break;
                    }
                    System.out.print("   ");
                }

                //储存到数据库
                bmAllService.insertPo(stu);
                System.out.println("   ");
            }
        }
    }




}
