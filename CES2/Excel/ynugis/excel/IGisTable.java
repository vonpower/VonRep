package ynugis.excel;

import java.io.FileOutputStream;
import java.io.IOException;

public interface IGisTable {

	/* (non-Javadoc)
	 * @see ITable#initial(java.io.FileOutputStream)
	 */
	public abstract void initial(FileOutputStream fos) throws Exception;

	/* (non-Javadoc)
	 * @see ITable#writeCell(java.lang.String, int, int)
	 */
	public abstract void writeCell(String strCellValue, int row, int column);

	

	/* (non-Javadoc)
	 * @see ITable#writerow(java.lang.String[], int)
	 */
	public abstract void writerow(String[] value, int rowIdx);

	/* (non-Javadoc)
	 * @see ITable#writerow(java.lang.String[], int, int)
	 */
	public abstract void writerow(String[] value, int rowIdx, int startIdx);

	/* (non-Javadoc)
	 * @see ITable#save()
	 */
	public abstract void save() throws IOException;

}