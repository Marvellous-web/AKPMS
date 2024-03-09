package argus.util;


public class JsonResponse {


	private Object rows = null;

	private int page;

	private long total;


	public JsonResponse(int page, long total, Object rows) {
		this.page = page;
		this.total = total;
		this.rows = rows;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}
