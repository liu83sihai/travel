package com.dce.business.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author 未知
 * poi Excel创建工具
 * @time 2017年9月25日15:35:14 添加复杂表头 tangkmf
 */
@SuppressWarnings({"rawtypes","deprecation", "unused"})
public class ExeclTools {
    public final static Double defaultDoubleValue = -1D;
    public final static Integer defaultIntValue = -1;

    /**
     * 导出复杂表头
     *
     * @param sheetName
     * @param execlhearList
     * @param keys
     * @param pojoList
     * @return
     */
    public static <T, E> HSSFWorkbook execlExport(
            String sheetName,
            List<List<T>> execlhearList,
            String[] keys, List<E> pojoList
    ) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        wb.setSheetName(0, sheetName);
        addHear(sheet, execlhearList);
        addBody(sheet, keys, pojoList, execlhearList.size());
        return wb;
    }

    /**
     * 添加数据
     *
     * @param sheet
     * @param keys     数据对应的Key
     * @param pojoList 数据
     * @param h        数据开始的行
     * @return
     */
    private static <E> HSSFSheet addBody(HSSFSheet sheet, String[] keys, List<E> pojoList, int h) {
        if (pojoList == null || pojoList.size() < 1) return sheet;
        HSSFWorkbook wb = sheet.getWorkbook();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFRow row = null;
        HSSFCell cell = null;

        int dataLenght = pojoList.size();
        for (int rowIndex = 0; rowIndex < dataLenght; rowIndex++) {
            Object temp = pojoList.get(rowIndex);
            row = sheet.createRow(rowIndex + h);
            if (temp instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> rowData = (Map<String, Object>) temp;

                for (int colIndex = 0; colIndex < keys.length; colIndex++) {
                    cell = row.createCell(colIndex);
                    Object dataValue = rowData.get(keys[colIndex]);
                    setCellValue(cell, dataValue);
                }
            } else {
                for (int colIndex = 0; colIndex < keys.length; colIndex++) {
                    cell = row.createCell(colIndex);
                    String dataIndex = keys[colIndex];
                    String methodString = "get"
                            + dataIndex.substring(0, 1).toUpperCase()
                            + dataIndex.substring(1, dataIndex.length());
                    Object dataValue = ReflectionUtils.invokeMethod(temp, methodString, null);
                    cell.setCellStyle(cellStyle);
                    setCellValue(cell, dataValue);
                }
            }
        }

        return sheet;
    }

    /**
     * 将值设置到对应的表格
     *
     * @param cell 表格
     * @param obj  值
     */
    private static void setCellValue(HSSFCell cell, Object obj) {
        if (obj instanceof String) {
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue((String) obj);

            // 值为数字
        } else if (obj instanceof BigDecimal) {
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(((BigDecimal) obj).doubleValue());

            // 值为日期
        } else if (obj instanceof Double) {
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(((Double) obj));

        } else if (obj instanceof Date) {
            cell.setCellValue((Date) obj);
            HSSFCellStyle cellStyle = cell.getSheet().getWorkbook().createCellStyle();
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            cell.setCellStyle(cellStyle);
        } else if (obj instanceof Integer) {
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue((Integer) obj);
        } else {
            if (obj == null) {
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue("");
            } else {
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(String.valueOf(obj));
            }
        }

    }

    /**
     * 添加复杂表头
     *
     * @param sheet
     * @param execlhearList
     * @return
     */
    private static <T> HSSFSheet addHear(HSSFSheet sheet, List<List<T>> execlhearList) {
        HSSFWorkbook wb = sheet.getWorkbook();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFCell cell;
        HSSFRow row;

        for (int rowIndex = 0; rowIndex < execlhearList.size(); rowIndex++) {
            List<T> rowHearList = execlhearList.get(rowIndex);//遍历一行表头
            row = sheet.createRow(rowIndex);//创建一行表头
            for (int colIndex = 0, curColIndex = 0; colIndex < rowHearList.size(); colIndex++) {//遍历rowIndex所在行
                T c = rowHearList.get(colIndex);//获得表头的值
                cell = row.createCell(curColIndex);//在改行创建表头
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle);
                if (c instanceof IHSSFHeaderCell) {//复杂表头
                    String name = ReflectionUtils.invokeMethod(c, "getName", "NoValue");//获得名字
                    Double width = ReflectionUtils.invokeMethod(c, "getWidth", defaultDoubleValue);//获得宽度
                    //Integer firstCol = ReflectionUtils.invokeMethod(c,"getFirstCol", defaultIntValue);//获得多行合一的的起始列
                    Integer colNum = ReflectionUtils.invokeMethod(c, "getColNum", defaultIntValue);//获得合并几行
                    //以后添加参数可以直接在这里添加，在接口层加入参数，
                    cell.setCellValue(name);
                    if (width != null && defaultDoubleValue != width) {
                        sheet.setColumnWidth(colIndex, width.intValue());
                    }
                    if (colNum != null && colNum != defaultIntValue) {
                        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, curColIndex, curColIndex + colNum - 1));
                        curColIndex += colNum - 1;
                    }
                    curColIndex++;
                } else {//普通表头
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue((String) c);
                    curColIndex++;
                }
            }
        }
        return sheet;
    }

    /**
     * 通过模板导出逾期订单、贷款订单
     *
     * @param excelData
     * @param sheetName
     * @param pojoList
     * @param path          对应模板的文件路径
     * @param <T>
     * @return
     */
	public static <T> HSSFWorkbook execlExportByModule(String[] excelData, String sheetName, List<T> pojoList, String path) throws IOException {

        InputStream in = new FileInputStream(new File(path));
        HSSFWorkbook wb = new HSSFWorkbook(in);
        in.close();
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;
        int dataStringLength = excelData.length;
        int count = pojoList.size();
        int a = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 写实体
        for (int i = 0; i < count; i++) {
            Object temp = pojoList.get(i);
            row = sheet.getRow(i+2);    //空出来了11行
            a = 0;
            if (temp instanceof Map) {
                Map map = (Map) temp;
                for (int dataInt = 0; dataInt < dataStringLength; dataInt++) {
                    cell = row.getCell(a++);
                    Object obj = map.get(excelData[dataInt]);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    if (obj == null) {
                        cell.setCellValue("");
                    }  else if (obj instanceof BigDecimal) {
                        cell.setCellValue(((BigDecimal) obj).intValue());
                    } else if (obj instanceof Double) {
                        cell.setCellValue(((Double) obj));
                    }  else if (obj instanceof Integer) {
                        cell.setCellValue((Integer) obj);
                    }else if (obj instanceof Long) {
                        cell.setCellValue((Long) obj);
                    } else if (obj instanceof Date) {
                        cell.setCellValue(df.format((Date) obj));
                    } else {
                        cell.setCellValue((String.valueOf(obj)));
                    }
                }

            } else { // List<pojo> 处理
                Class c = temp.getClass();
                // 获取POJO所有方法
                Method m[] = c.getDeclaredMethods();

                for (int dataInt = 0; dataInt < dataStringLength; dataInt++) {
                    cell = row.getCell(a++);
                    // 数据索引
                    String dataIndex = excelData[dataInt];

                    String methodString = "get"
                            + dataIndex.substring(0, 1).toUpperCase()
                            + dataIndex.substring(1, dataIndex.length());
                    // 根据循环获取索引值
                    for (int k = 0; k < m.length; k++) {
                        if (m[k].getName().equals(methodString)) {
                            // System.out.println("方法名："+m[i].getName());
                            try {
                                Object obj = m[k].invoke(temp);
                                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                if (obj == null) {
                                    cell.setCellValue("");
                                } else if (obj instanceof BigDecimal) {
                                    cell.setCellValue(((BigDecimal) obj).intValue());
                                } else if (obj instanceof Double) {
                                    cell.setCellValue(((Double) obj));
                                }  else if (obj instanceof Integer) {
                                    cell.setCellValue((Integer) obj);
                                }else if (obj instanceof Long) {
                                    cell.setCellValue((Long) obj);
                                }  else if (obj instanceof Date) {
                                    cell.setCellValue(df.format((Date) obj));
                                } else {
                                    cell.setCellValue((String.valueOf(obj)));
                                }
                                break;
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        return wb;

    }


    /**
     * 根据List对象导出 execl
     *
     * @param <T>
     * @param execlhearList 表头
     * @param excelData     表索引
     * @param sheetName     表格名(英文)
     * @param pojoList      导出值
     * @return
     */
    public static <T> HSSFWorkbook execlExport(List<String[]> execlhearList,
                                               String[] excelData, 
                                               String sheetName, 
                                               List<T> pojoList){
    	return execlExport(execlhearList,excelData,sheetName,pojoList,null);
    	
    }
                                               
    /**
     * 根据List对象导出 execl
     *
     * @param <T>
     * @param execlhearList 表头
     * @param excelData     表索引
     * @param sheetName     表格名(英文)
     * @param pojoList      导出值
     * @return
     */
	public static <T> HSSFWorkbook execlExport(List<String[]> execlhearList,
                                               String[] excelData, 
                                               String sheetName, 
                                               List<T> pojoList,
                                               Map<String,CellFormatter> cellFormatterMap) {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setDefaultColumnWidth(20);
        wb.setSheetName(0, sheetName);
        HSSFCell cell;
        // 表头
        HSSFRow row;
        int execlHearSize = execlhearList.size();
        for (int i = 0; i < execlHearSize; i++) {
            String[] execlhear = execlhearList.get(i);
            row = sheet.createRow((int) i);
            int tempStringLength = execlhear.length;
            for (int a = 0; a < tempStringLength; a++) {
                cell = row.createCell(a);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(execlhear[a]);
            }
        }
        int dataStringLength = excelData.length;
        int count = pojoList.size();

        int a = 0;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HSSFCellStyle cellStyle = wb.createCellStyle(); // 建立新的cell样式
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

        // 写实体
        for (int i = 0; i < count; i++) {
            Object temp = pojoList.get(i);
            row = sheet.createRow((int) i + execlHearSize);
            a = 0;

            if (temp instanceof Map) {
                Map map = (Map) temp;

                for (int dataInt = 0; dataInt < dataStringLength; dataInt++) {
                    cell = row.createCell(a++);
                    Object obj = map.get(excelData[dataInt]);
                    
                    //格式转换输出,根据列的头找对应的格式化类
                    if(cellFormatterMap != null){
                    	CellFormatter cf = cellFormatterMap.get(excelData[dataInt]);
                    	if(cf != null){
                    		obj = cf.formatterValue(obj);
                    	}
                    }
                    
                    // 值是字符串
                    if (obj instanceof String) {
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue((String) obj);

                        // 值为数字
                    } else if (obj instanceof BigDecimal) {
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(((BigDecimal) obj).doubleValue());

                        // 值为日期
                    } else if (obj instanceof Double) {
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(((Double) obj));

                    } else if (obj instanceof Date) {
                        cell.setCellValue(df.format((Date) obj));
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//						cell.setCellValue((Date) obj);
//						cell.setCellStyle(cellStyle);
                    } else if (obj instanceof Integer) {
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue((Integer) obj);
                    } else if (obj instanceof Byte) {
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue((Byte) obj);
                    }else {
                        if (obj == null) {
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            cell.setCellValue("");
                        } else {
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            cell.setCellValue(String.valueOf(obj));
                        }
                    }

                }

            } else { // List<pojo> 处理
                Class c = temp.getClass();
                // 获取POJO所有方法
                Method m[] = c.getDeclaredMethods();

                for (int dataInt = 0; dataInt < dataStringLength; dataInt++) {
                    cell = row.createCell((short) a++);
                    // 数据索引
                    String dataIndex = excelData[dataInt];

                    String methodString = "get"
                            + dataIndex.substring(0, 1).toUpperCase()
                            + dataIndex.substring(1, dataIndex.length());
                    // 根据循环获取索引值
                    for (int k = 0; k < m.length; k++) {
                        if (m[k].getName().equals(methodString)) {
                            // System.out.println("方法名："+m[i].getName());
                            try {
                                Object obj = m[k].invoke(temp);
                                // 值是字符串
                                if (obj instanceof String) {
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    cell.setCellValue((String) obj);

                                    // 值为数字
                                } else if (obj instanceof BigDecimal) {
                                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                    cell.setCellValue(((BigDecimal) obj)
                                            .doubleValue());

                                } else if (obj instanceof Double) {
                                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                    cell.setCellValue(((Double) obj));

                                } else if (obj instanceof Long) {
                                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                    cell.setCellValue(((Long) obj));

                                    // 值为日期
                                } else if (obj instanceof Date) {
                                    cell.setCellValue(df.format((Date) obj));
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//									cell.setCellValue((Date) obj);
//									cell.setCellStyle(cellStyle);
                                } else if (obj instanceof Integer) {
                                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                    cell.setCellValue((Integer) obj);
                                } else if (obj instanceof Byte) {
                                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                    cell.setCellValue((Byte) obj);
                                } else {
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    cell.setCellValue((String) obj);
                                }

                                break;
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        return wb;

    }
    
    /**
     * 根据List对象导出 execl（仿excelExport()方法,仅适用于账龄分析报表，行列转换，其他一样）
     *
     * @param <T>
     * @param execlhearList 表头
     * @param excelData     表索引
     * @param sheetName     表格名(英文)
     * @param pojoList      导出值
     * @return
     */
    public static <T> HSSFWorkbook execlConvertExport(List<String[]> execlhearList,
                                               String[] excelData, String sheetName, List<T> pojoList) {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setDefaultColumnWidth(20);
        wb.setSheetName(0, sheetName);
        HSSFCell cell;
        // 表头
        HSSFRow row;
        int execlHearSize = execlhearList.size();
        for (int i = 0; i < execlHearSize; i++) {
            String[] execlhear = execlhearList.get(i);
            row = sheet.createRow((int) i);
            int tempStringLength = execlhear.length;
            
            int a = 1;
            cell = row.createCell(a);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("逾期期数");
            
            for (a = 2; a < tempStringLength; a++) {
                cell = row.createCell(a);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(execlhear[a]);
            }
        }
        int dataStringLength = excelData.length;
        int count = pojoList.size();

        int a = 0;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HSSFCellStyle cellStyle = wb.createCellStyle(); // 建立新的cell样式
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        
        

        int rowCount = 0;
        for(int dataI = 0;dataI<excelData.length;dataI++){
        	row = sheet.createRow((int) rowCount++ + execlHearSize);
        	String data = excelData[dataI];
        	
        	CellRangeAddress craM = new CellRangeAddress(0, 1, 0, 0);
        	sheet.addMergedRegion(craM);
        	Cell cellM = row.createCell(0);  
        	cellM.setCellType(HSSFCell.CELL_TYPE_STRING);
        	cellM.setCellValue(" ");
        	
        	if(row.getRowNum() == 2){
        		
        		CellRangeAddress craM1 = new CellRangeAddress(2, 5, 0, 0);
            	sheet.addMergedRegion(craM1);
            	Cell cellM1 = row.createCell(0);  
            	cellM1.setCellType(HSSFCell.CELL_TYPE_STRING);
            	cellM1.setCellValue("M1");
        	}
        	
        	//用row.getRowNum() == XX就行了，说明后面的行的单元格可能覆盖了，导致美数据；
        	if(row.getRowNum()  == 6){
	        	CellRangeAddress craM2 = new CellRangeAddress(6, 9, 0, 0);
	        	sheet.addMergedRegion(craM2);
	        	Cell cellM2 = row.createCell(0);  
	        	cellM2.setCellType(HSSFCell.CELL_TYPE_STRING);
	        	cellM2.setCellValue("M2");
        	}
        	
        	if(row.getRowNum()  == 10){
	        	CellRangeAddress craM3 = new CellRangeAddress(10, 13, 0, 0);
	        	sheet.addMergedRegion(craM3);
	        	Cell cellM3 = row.createCell(0);  
	        	cellM3.setCellType(HSSFCell.CELL_TYPE_STRING);
	        	cellM3.setCellValue("M3");
        	}
        	
        	if(row.getRowNum()  == 14){
	        	CellRangeAddress craM4 = new CellRangeAddress(14, 17, 0, 0);
	        	sheet.addMergedRegion(craM4);
	        	Cell cellM4 = row.createCell(0);  
	        	cellM4.setCellType(HSSFCell.CELL_TYPE_STRING);
	        	cellM4.setCellValue("M4");
        	}
        	
        	if(row.getRowNum()  == 18){
	        	CellRangeAddress craM2Plus = new CellRangeAddress(18, 21, 0, 0);
	        	sheet.addMergedRegion(craM2Plus);
	        	Cell cellM2Plus = row.createCell(0);  
	        	cellM2Plus.setCellType(HSSFCell.CELL_TYPE_STRING);
	        	cellM2Plus.setCellValue("M2+");
        	}
        	
        	if(row.getRowNum()  == 22){
	        	CellRangeAddress craM4Plus = new CellRangeAddress(22, 25, 0, 0);
	        	sheet.addMergedRegion(craM4Plus);
	        	Cell cellM4Plus = row.createCell(0);  
	        	cellM4Plus.setCellType(HSSFCell.CELL_TYPE_STRING);
	        	cellM4Plus.setCellValue("M4+");
        	}
        	
        	
        	a = 1;
        	cell = row.createCell(a++);
        	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        	if(data.endsWith("OverdueAmount")){
        		cell.setCellValue("逾期金额");
        	}else if(data.endsWith("OverdueCount")){
        		cell.setCellValue("逾期人数");
        	}else if(data.endsWith("Spot")){
        		cell.setCellValue("逾期率coincidental%");
        	}else if(data.endsWith("Defer")){
        		cell.setCellValue("逾期率lagged%");
        	}else if(data.endsWith("storeAmt")){
        		cell.setCellValue("应收账款");
        	}
        	
        	for(String head : execlhearList.get(0)){
                // 写实体
        		
                for (int i = 0; i < count; i++) {
                    Object temp = pojoList.get(i);
                    

                    if (temp instanceof Map) {
                        Map map = (Map) temp;
                        String dateInMap = (String)map.get("date");
                        if(dateInMap.equals(dateInMap)){
                        	Object obj = map.get(data);
                        	// 值是字符串
                        	cell = row.createCell(a++);
                        	if (obj instanceof String) {
                                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                cell.setCellValue((String) obj);
                                // 值为数字
                            } else if (obj instanceof BigDecimal) {
                                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                cell.setCellValue(((BigDecimal) obj).doubleValue());

                                // 值为日期
                            } else if (obj instanceof Double) {
                                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                cell.setCellValue(((Double) obj));

                            } else if (obj instanceof Date) {
                                cell.setCellValue(df.format((Date) obj));
                                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            } else if (obj instanceof Integer) {
                                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                cell.setCellValue((Integer) obj);
                            } else {
                                if (obj == null) {
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    cell.setCellValue("");
                                } else {
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    cell.setCellValue(String.valueOf(obj));
                                }
                            }
                          }

                    } else { // List<pojo> 处理(pojo暂未做处理,17.11.27)
                        Class c = temp.getClass();
                        // 获取POJO所有方法
                        Method m[] = c.getDeclaredMethods();

                        for (int dataInt = 0; dataInt < dataStringLength; dataInt++) {
                            cell = row.createCell((short) a++);
                            // 数据索引
                            String dataIndex = excelData[dataInt];

                            String methodString = "get"
                                    + dataIndex.substring(0, 1).toUpperCase()
                                    + dataIndex.substring(1, dataIndex.length());
                            // 根据循环获取索引值
                            for (int k = 0; k < m.length; k++) {
                                if (m[k].getName().equals(methodString)) {
                                    // System.out.println("方法名："+m[i].getName());
                                    try {
                                        Object obj = m[k].invoke(temp);
                                        // 值是字符串
                                        if (obj instanceof String) {
                                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                            cell.setCellValue((String) obj);

                                            // 值为数字
                                        } else if (obj instanceof BigDecimal) {
                                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                            cell.setCellValue(((BigDecimal) obj)
                                                    .doubleValue());

                                        } else if (obj instanceof Double) {
                                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                            cell.setCellValue(((Double) obj));

                                        } else if (obj instanceof Long) {
                                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                            cell.setCellValue(((Long) obj));

                                            // 值为日期
                                        } else if (obj instanceof Date) {
                                            cell.setCellValue(df.format((Date) obj));
                                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//        									cell.setCellValue((Date) obj);
//        									cell.setCellStyle(cellStyle);
                                        } else if (obj instanceof Integer) {
                                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                            cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                                            cell.setCellStyle(cellStyle);
                                            cell.setCellValue((Integer) obj);
                                        } else {
                                        	cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                            cell.setCellStyle(cellStyle);
                                            cell.setCellValue((String) obj);
                                        }

                                        break;
                                    } catch (IllegalArgumentException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
        	}
        }
        


        return wb;

    }

    /**
     * 读取单元格的数据
     *
     * @param row
     * @param cell
     * @return
     * @throws NullPointerException
     */
    public static String processCellValue(HSSFRow row, int cell)
            throws NullPointerException {
        HSSFCell headCell = row.getCell(cell);

        if (null == headCell) {
            return "";
        }

        String itemName = "";
        if (HSSFCell.CELL_TYPE_STRING == headCell.getCellType()) {
            itemName = headCell.getStringCellValue();
        }

        if (HSSFCell.CELL_TYPE_NUMERIC == headCell.getCellType()) {
            if (HSSFDateUtil.isCellDateFormatted(headCell)) {
                DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                double d = headCell.getNumericCellValue();
                Date date = HSSFDateUtil.getJavaDate(d);
                itemName = formater.format(date);
            } else {
                BigDecimal a = new BigDecimal(String.valueOf(headCell
                        .getNumericCellValue()));
                itemName = (a.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
            }
        }

        if (HSSFCell.CELL_TYPE_BOOLEAN == headCell.getCellType()) {
            itemName = String.valueOf(headCell.getBooleanCellValue());
        }

        if (HSSFCell.CELL_TYPE_BLANK == headCell.getCellType()) {
            itemName = "";
        }

        if (HSSFCell.CELL_TYPE_FORMULA == headCell.getCellType()) {
            itemName = headCell.getCellFormula();
        }
        return itemName;
    }

}
