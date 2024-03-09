package argus.util;

import java.io.Serializable;
import java.util.List;

/**
 * Wrapper class for JSON data to send to the client.
 *
 * Currently we are using jQuery FlexGrid for displaying JSON data in table.
 *
 *
 * jQuery FlexGrid plug-in requires data to be in the below specified format.
 *
 *
 * total: (no of rec) page : (page no) rows : [{id: idVal, cell: [ (col1 value)
 * , (col2 value) ,.. ]}, {id: idVal, cell: [ (col1 value) , (col2 value) ,.. ]}
 * ]
 *
 * To keep the data service independent of this requirement as far as possible,
 * the id, cell format specifically ignored. we wrap the result from the data
 * service and further format the result using JavaScript as required.
 *
 *
 * @author IDS ARGUS
 *
 * @version 1.0
 * @see <a href="http://flexigrid.info/">jQuery FlexGrid</a>
 *
 * @param T
 *            generic data type for list of objects to be sent in the JSON
 *            response.
 */
public class JsonDataWrapper<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// current page
	private int page;
	// total number of records for the given entity.
	private long total;
	// list of records to be displayed.
	private List<T> rows;

	public JsonDataWrapper(int page, long total, List<T> rows) {
		this.page = page;
		this.total = total;
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

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
