package com.zitop.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zitop.tracking.entity.CustomerCategory;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.IndexItem;

public class DataItemUtil {
	public static int right=1;//右移动
	public static int  up=1;//下移动
	private final static Log log = LogFactory.getLog(DataItemUtil.class);

	/**
	 * 读取xls文件内容
	 * @param filePath
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<List> read(String filePath) throws BiffException, IOException {
		List<List> rowList = null;

		File file = new File(filePath);
		if (!file.isFile()) {
			log.error("无效文件-" + filePath);
			return null;
		}
		if (!file.getName().endsWith("xls")) {
			log.error("不是xls文件-" + filePath);
			return null;
		}
		// 打开文件
		Workbook book = Workbook.getWorkbook(file);
		// 取得第一个sheet
		Sheet sheet = book.getSheet(0);
		// 取得行数
		int rows = sheet.getRows();
		// 取得列数
		int cols = sheet.getColumns();
		rowList = new ArrayList<List>();
		for (int i = 0; i < rows; i++) {
			Cell[] cell = sheet.getRow(i);
			List<String> cellList = new ArrayList<String>();
			for (int j = 0; j < cols; j++) {
				// getCell(列，行)
				String content="";
				if(cell.length>=j){
					content = sheet.getCell(j, i).getContents();
				}
				cellList.add(content);
			}
			rowList.add(cellList);
		}
		// 关闭文件
		book.close();
		return rowList;
	}
    
	public static boolean write(OutputStream os,Map<String,List<CustomerCategory>> colMap,Map<String,Map<IndexCategory,List<IndexItem>>> rowMap){
		WritableWorkbook book;
		try {
			book = Workbook.createWorkbook(os);
			WritableSheet sheet=book.createSheet("sheet", 0);
			sheet.getSettings().setHorizontalFreeze(5);
			sheet.getSettings().setVerticalFreeze(3);
			/*横向*/
			setColHead(sheet,colMap);
			/*纵向*/
			setRowHead(sheet,rowMap);
			sheet.setColumnView(0, 0);
			sheet.setRowView(0, 0);
			sheet.addCell(new Label(0,0,"true"));
//			System.out.println(sheet.getRows());
			book.write();
			book.close();
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (RowsExceededException e) {
			e.printStackTrace();
			return false;
		} catch (WriteException e) {
			e.printStackTrace();
			return false;
		}
		log.info("write successful");
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	private static void setColHead(WritableSheet sheet,Map<String,List<CustomerCategory>> map) throws RowsExceededException, WriteException {
		for(int i=0;i<4;i++){
			sheet.mergeCells(i+right, 0+up, i+right, 1+up);
		}
		WritableCellFormat cf1=new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),11,WritableFont.BOLD));
		cf1.setAlignment(Alignment.CENTRE);
		sheet.addCell(new Label(0+right,0+up,"模块",cf1));
		sheet.setColumnView(0+right, 13);
		sheet.addCell(new Label(1+right,0+up,"分类",cf1));
		sheet.setColumnView(1+right, 17);
		sheet.addCell(new Label(2+right,0+up,"指标",cf1));
		sheet.setColumnView(2+right, 40);
		sheet.addCell(new Label(3+right,0+up,"呈现方式",cf1));
//		sheet.addCell(new Label(4+right,1+up,"总体",cf1));
//		sheet.addCell(new Label(4+right,0+up,"总体",cf1));
		Set<String> key = map.keySet();
		int i=4+right;//开始列
		for(Iterator it = key.iterator(); it.hasNext();){
			String str = (String) it.next();
			List<CustomerCategory> customerCategories=map.get(str);
			int n=customerCategories.size();
			sheet.mergeCells(i, 0+up, i+n-1, 0+up);
			sheet.addCell(new Label(i,0+up,str,cf1));
			if(str.length()<3){
				sheet.setColumnView(i,(int)(str.length()*5));
			}else{
				sheet.setColumnView(i,(int)(str.length()*2.5));
			}
			int j=0;//开始累加列
			for(CustomerCategory customerCategory:customerCategories){
				String strId="";
				if(customerCategory.getId()!=null){
					strId=customerCategory.getId().toString();
				}
				sheet.addCell(new Label(i+j,0,strId,cf1));
				sheet.addCell(new Label(i+j,1+up,customerCategory.getName(),cf1));
				sheet.setColumnView(i+j,(int)(customerCategory.getName().length()*2.5));
				j++;
			}
			i+=n;
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	private static void setRowHead(WritableSheet sheet,Map<String,Map<IndexCategory,List<IndexItem>>> map) throws RowsExceededException, WriteException {
		WritableCellFormat cf1=new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),11,WritableFont.NO_BOLD));
		cf1.setVerticalAlignment(VerticalAlignment.CENTRE);
		cf1.setWrap(true);
		WritableCellFormat cf2=new WritableCellFormat(cf1);
		cf1.setAlignment(Alignment.CENTRE);
		Set<String> key = map.keySet();
		int i=2+up;//开始行
		for(Iterator it = key.iterator(); it.hasNext();){
			int m=0;//记录模块行数
			String str = (String) it.next();
			Map<IndexCategory,List<IndexItem>> subMap=map.get(str);
			Set<IndexCategory> subKey = subMap.keySet();
			int j=i;//2级分类开始行
			for(Iterator it1 = subKey.iterator(); it1.hasNext();){
				IndexCategory indexCategory = (IndexCategory) it1.next();
				List<IndexItem> indexItems=subMap.get(indexCategory);
				int n=indexItems.size();
				sheet.mergeCells(1+right, j, 1+right, j+n-1);
				sheet.addCell(new Label(1+right,j,indexCategory.getName(),cf1));
				sheet.mergeCells(3+right, j, 3+right, j+n-1);
				sheet.addCell(new Label(3+right,j,IndexCategory.GRAPH_TYPE_ARRAY[indexCategory.getGraphType()],cf1));//呈现方式
				int k=0;//开始累加行
				for(IndexItem indexItem:indexItems){
//					System.out.println(indexItem.getName());
					String strId="";
					if(indexItem.getId()!=null){
						strId=indexItem.getId().toString();
					}
					sheet.addCell(new Label(0,j+k,strId,cf2));
					sheet.addCell(new Label(2+right,j+k,indexItem.getName(),cf2));
					int hight=(indexItem.getName().length()/18)+1;
//					System.out.println(hight);
					sheet.setRowView(j+k, (int)(hight*300));
					k++;
				}
				j+=n;
				m+=n;
			}
//			System.out.println(sheet.getRows());
			sheet.mergeCells(0+right,i,0+right,i+m-1);
			sheet.addCell(new Label(0+right,i,str,cf1));
			i+=m;
		}
			
	}
	
	@SuppressWarnings("rawtypes")
	public static void createData(Map<String,List<CustomerCategory>> colMap,Map<String,Map<IndexCategory,List<IndexItem>>> rowMap) throws RowsExceededException, WriteException, IOException {
		OutputStream os = null;
		WritableWorkbook book = null;
		WritableSheet sheet = null;
		try {
			os = new FileOutputStream(new File("d:/testdata.xls"));
			book = Workbook.createWorkbook(os);
			sheet=book.createSheet("testdata", 0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] oneCols=new int[colMap.size()];
		Set<String> key = colMap.keySet();
		int i=0;
		List<CustomerCategory> colList=new ArrayList<CustomerCategory>();
		List<IndexItem> rowList=new ArrayList<IndexItem>();
		for(Iterator it = key.iterator(); it.hasNext();){
			String str = (String) it.next();
			List<CustomerCategory> customerCategories=colMap.get(str);
			oneCols[i]=customerCategories.size();
			colList.addAll(customerCategories);
			i++;
		}
		Set<String>rowKey=rowMap.keySet();
		for(Iterator itRow = rowKey.iterator(); itRow.hasNext();){
			String rowStr=(String)itRow.next();
			Map<IndexCategory,List<IndexItem>> map=rowMap.get(rowStr);
			Set<IndexCategory> key2=map.keySet();
			for(Iterator iterator=key2.iterator();iterator.hasNext();){
				IndexCategory category=(IndexCategory) iterator.next();
				List<IndexItem> indexItems=map.get(category);
				rowList.addAll(indexItems);
			}
		}
		
		for(int rowNum=0;rowNum<rowList.size();rowNum++){
			int colNum=0;
			for(int s=0;s<oneCols.length;s++){
				int total=30;
				int a=oneCols[s];
				for(int m=0;m<a;m++){
					int rand=(int)(Math.random()*total);
					if(colNum==0){
						sheet.addCell(new Label(colNum,rowNum,(int)(30+Math.random()*10)+""));
					}
					else{
						sheet.addCell(new Label(colNum,rowNum,""+rand));
					}
					colNum++;
					total=total-rand;
				}
			}
		}
		book.write();
		book.close();
		os.flush();
		os.close();
		
	}
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws BiffException, IOException, RowsExceededException, WriteException {
		String path = "D:/我的资料库/Downloads/移动互联网行为跟踪宽表模板.xls";
		OutputStream os=new FileOutputStream("d:/test.xls");
		Map<String,List<CustomerCategory>> colMap=new TreeMap<String, List<CustomerCategory>>();
		List<CustomerCategory> customerCategories=new ArrayList<CustomerCategory>();
		for(int i=0;i<4;i++){
			CustomerCategory customerCategory=new CustomerCategory();
			customerCategory.setName("row"+i);
			customerCategories.add(customerCategory);
		}
	
		for(int j=0;j<3;j++){
			colMap.put("col"+j, customerCategories);
		}
		
		Map<String,Map<IndexCategory,List<IndexItem>>> rowMap=new TreeMap<String,Map<IndexCategory,List<IndexItem>>>();
		Map<IndexCategory,List<IndexItem>> map=new TreeMap<IndexCategory,List<IndexItem>>(new IndexCategoryComparator());
		List<IndexItem> indexItems=new ArrayList<IndexItem>();
		for(int i=0;i<3;i++){
			IndexItem indexItem=new IndexItem();
			indexItem.setName("index"+i);
			indexItems.add(indexItem);
		}
		for(int  i=0;i<3;i++){
			IndexCategory indexCategory=new IndexCategory();
			indexCategory.setName("lv2"+i);
			indexCategory.setId((long) i);
			map.put(indexCategory, indexItems);
		}
		for(int  i=0;i<3;i++){
			rowMap.put("lv1_"+i, map);
		}
		
		
		
		
		write(os,colMap,rowMap);
		List<List> result = read(path);
		if (result != null) {
			for (List<String> cell : result) {
				for (int i = 0; i < cell.size(); i++) {
					System.out.print(cell.get(i) + "\t");
				}
				System.out.println("\r\n");
			}
		}
	}
}
