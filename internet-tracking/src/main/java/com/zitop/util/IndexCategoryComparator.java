package com.zitop.util;

import java.util.Comparator;

import com.zitop.tracking.entity.IndexCategory;

public class IndexCategoryComparator implements Comparator<IndexCategory> {
	@Override
	public int compare(IndexCategory o1, IndexCategory o2) {
		// 如果有空值，直接返回0
		if (o1 == null || o2 == null)
		return 0;
		if (o1.getId() == null || o2.getId()== null)
		return 0;
		return o1.getId().compareTo(o2.getId());

	}
}
