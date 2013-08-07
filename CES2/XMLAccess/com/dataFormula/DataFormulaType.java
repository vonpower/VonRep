/**
 * DataFormulaType.java
 *
 * This file was generated by XMLSpy 2006sp2 Enterprise Edition.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the XMLSpy Documentation for further details.
 * http://www.altova.com/xmlspy
 */


package com.dataFormula;


public class DataFormulaType extends com.altova.xml.Node {

	public DataFormulaType(DataFormulaType node) {
		super(node);
	}

	public DataFormulaType(org.w3c.dom.Node node) {
		super(node);
	}

	public DataFormulaType(org.w3c.dom.Document doc) {
		super(doc);
	}

	public DataFormulaType(com.altova.xml.Document doc, String namespaceURI, String prefix, String name) {
		super(doc, namespaceURI, prefix, name);
	}
	
	public void adjustPrefix() {
		for (	org.w3c.dom.Node tmpNode = getDomFirstChild( Element, null, "DataCatalog" );
				tmpNode != null;
				tmpNode = getDomNextChild( Element, null, "DataCatalog", tmpNode )
			) {
			internalAdjustPrefix(tmpNode, true);
			new DataCatalogType(tmpNode).adjustPrefix();
		}
	}

	public static int getDataCatalogMinCount() {
		return 1;
	}

	public static int getDataCatalogMaxCount() {
		return 1;
	}

	public int getDataCatalogCount() {
		return getDomChildCount(Element, null, "DataCatalog");
	}

	public boolean hasDataCatalog() {
		return hasDomChild(Element, null, "DataCatalog");
	}

	public DataCatalogType newDataCatalog() {
		return new DataCatalogType(domNode.getOwnerDocument().createElementNS(null, "DataCatalog"));
	}

	public DataCatalogType getDataCatalogAt(int index) throws Exception {
		return new DataCatalogType(dereference(getDomChildAt(Element, null, "DataCatalog", index)));
	}

	public org.w3c.dom.Node getStartingDataCatalogCursor() throws Exception {
		return getDomFirstChild(Element, null, "DataCatalog" );
	}

	public org.w3c.dom.Node getAdvancedDataCatalogCursor( org.w3c.dom.Node curNode ) throws Exception {
		return getDomNextChild( Element, null, "DataCatalog", curNode );
	}

	public DataCatalogType getDataCatalogValueAtCursor( org.w3c.dom.Node curNode ) throws Exception {
		if( curNode == null )
			throw new com.altova.xml.XmlException("Out of range");
		else
			return new DataCatalogType( dereference(curNode) );
	}

	public DataCatalogType getDataCatalog() throws Exception 
 {
		return getDataCatalogAt(0);
	}

	public void removeDataCatalogAt(int index) {
		removeDomChildAt(Element, null, "DataCatalog", index);
	}

	public void removeDataCatalog() {
		while (hasDataCatalog())
			removeDataCatalogAt(0);
	}

	public void addDataCatalog(DataCatalogType value) {
		appendDomElement(null, "DataCatalog", value);	
	}

	public void insertDataCatalogAt(DataCatalogType value, int index) {
		insertDomElementAt(null, "DataCatalog", index, value);
	}

	public void replaceDataCatalogAt(DataCatalogType value, int index) {
		replaceDomElementAt(null, "DataCatalog", index, value);
	}

	private org.w3c.dom.Node dereference(org.w3c.dom.Node node) {
		return node;
	}
}
