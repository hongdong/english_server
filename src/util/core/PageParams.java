package util.core;


public class PageParams {
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String searchAnds;
	private String searchColumnNames;
	private String searchConditions;
	private String searchVals;
	private String gridName;
	private String searchType;

	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getGridName() {
		return gridName;
	}
	public void setGridName(String gridName) {
		this.gridName = gridName;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}

	public String getSearchAnds() {
		return searchAnds;
	}
	public void setSearchAnds(String searchAnds) {
		this.searchAnds = searchAnds;
	}
	public String getSearchColumnNames() {
		return searchColumnNames;
	}
	public void setSearchColumnNames(String searchColumnNames) {
		this.searchColumnNames = searchColumnNames;
	}
	public String getSearchConditions() {
		return searchConditions;
	}
	public void setSearchConditions(String searchConditions) {
		this.searchConditions = searchConditions;
	}
	public String getSearchVals() {
		return searchVals;
	}
	public void setSearchVals(String searchVals) {
		this.searchVals = searchVals;
	}
	
	@Override
	public String toString() {
		String params="page:"+page+"|rows:"+rows+"|sort:"+sort+"|order:"+"|searchType:"+searchType+"|searchAnds:"+searchAnds+"|searchColumnNames:"+searchColumnNames+"|searchConditions:"+searchConditions+"|searchVals:"+searchVals;
		return params;
	}
}
