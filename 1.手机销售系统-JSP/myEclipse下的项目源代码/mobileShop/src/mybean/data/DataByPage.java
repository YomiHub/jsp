package mybean.data;

import com.sun.rowset.CachedRowSetImpl;

public class DataByPage {
	CachedRowSetImpl rowSet = null;    //�洢����ȫ����¼���м�����
	int pageSize = 2;   //ÿҳ��ʾ�ļ�¼��
	int totalPage =1;   //�ܼ�¼��
	int currentPage = 1;   //��ǰ��ʾҳ
	
	public CachedRowSetImpl getRowSet() {
		return rowSet;
	}
	public void setRowSet(CachedRowSetImpl rowSet) {
		this.rowSet = rowSet;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
}
