package com.zitop.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.zitop.tracking.entity.IndexCategory;

/**
 * 编码对照工具类 
 * @author Joseph
 */
public class SysCodeConst {
	private static final SysCodeConst sysCodeConst; 
	public static final Map<Integer, String> graphTypeToNameMap = new LinkedHashMap<Integer, String>();
	public static final Map<Integer, String> graphTypeToSeriesMap = new LinkedHashMap<Integer,String>();

	static{
		sysCodeConst = new SysCodeConst();
//		展现方式编码转换为名称
		graphTypeToNameMap.put(IndexCategory.GRAPHTYPE_LINE, "线图");
		graphTypeToNameMap.put(IndexCategory.GRAPHTYPE_PIE, "饼图 ");
		graphTypeToNameMap.put(IndexCategory.GRAPHTYPE_COLUMN, "柱形图");
		graphTypeToNameMap.put(IndexCategory.GRAPHTYPE_BAR, "条形图");
		graphTypeToNameMap.put(IndexCategory.GRAPHTYPE_AREA, "面状图");
		graphTypeToNameMap.put(IndexCategory.GRAPHTYPE_SCATTER, "散列图");
//		展现方式编码转换为图形类型
		graphTypeToSeriesMap.put(IndexCategory.GRAPHTYPE_LINE, "spline");
		graphTypeToSeriesMap.put(IndexCategory.GRAPHTYPE_PIE, "pie");
		graphTypeToSeriesMap.put(IndexCategory.GRAPHTYPE_COLUMN, "column");
		graphTypeToSeriesMap.put(IndexCategory.GRAPHTYPE_BAR, "bar");
		graphTypeToSeriesMap.put(IndexCategory.GRAPHTYPE_BAR, "bar");
		graphTypeToSeriesMap.put(IndexCategory.GRAPHTYPE_AREA, "area");
		graphTypeToSeriesMap.put(IndexCategory.GRAPHTYPE_SCATTER, "scatter");
		
	}
	
	/**
	 * 取得指标展现方式编码和含义的对照
	 */
	public Map<Integer,String> getGraphTypeToNameMap()
	{
		return graphTypeToNameMap;
	}
	/**
	 * 取得指标展现方式编码和图形类型 
	 */
	public Map<Integer,String> getGraphTypeToSeriesMap()
	{
		return graphTypeToSeriesMap;
	}
	public static SysCodeConst getInstance()
	{
		return sysCodeConst;
	}
	
}
