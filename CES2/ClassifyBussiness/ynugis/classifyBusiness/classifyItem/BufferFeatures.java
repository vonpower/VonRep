package ynugis.classifyBusiness.classifyItem;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.esri.arcgis.carto.FeatureLayer;
import com.esri.arcgis.carto.IFeatureLayer;
import com.esri.arcgis.carto.ISelection;
import com.esri.arcgis.controls.ControlsMapFullExtentCommand;
import com.esri.arcgis.controls.ControlsMapPanTool;
import com.esri.arcgis.controls.ControlsMapZoomInFixedCommand;
import com.esri.arcgis.controls.ControlsMapZoomInTool;
import com.esri.arcgis.controls.ControlsMapZoomOutFixedCommand;
import com.esri.arcgis.controls.ControlsMapZoomOutTool;
import com.esri.arcgis.controls.ControlsMapZoomPanTool;
import com.esri.arcgis.controls.ControlsMapZoomToLastExtentBackCommand;
import com.esri.arcgis.controls.ControlsMapZoomToLastExtentForwardCommand;
import com.esri.arcgis.controls.ControlsSelectFeaturesTool;
import com.esri.arcgis.controls.ControlsSelectTool;
import com.esri.arcgis.controls.MapControl;
import com.esri.arcgis.controls.TOCControl;
import com.esri.arcgis.controls.ToolbarControl;
import com.esri.arcgis.datasourcesfile.ShapefileWorkspaceFactory;
import com.esri.arcgis.display.IRgbColor;
import com.esri.arcgis.display.IRgbColorProxy;
import com.esri.arcgis.display.IScreenDisplay;
import com.esri.arcgis.display.ISimpleFillSymbol;
import com.esri.arcgis.display.ISymbol;
import com.esri.arcgis.display.ISymbolProxy;
import com.esri.arcgis.display.RgbColor;
import com.esri.arcgis.display.SimpleFillSymbol;
import com.esri.arcgis.display.esriSimpleFillStyle;
import com.esri.arcgis.geodatabase.Field;
import com.esri.arcgis.geodatabase.Fields;
import com.esri.arcgis.geodatabase.GeometryDef;
import com.esri.arcgis.geodatabase.ICursor;
import com.esri.arcgis.geodatabase.IEnumFeature;
import com.esri.arcgis.geodatabase.IEnumFeatureProxy;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureBuffer;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.geodatabase.IFeatureWorkspace;
import com.esri.arcgis.geodatabase.IFeatureWorkspaceProxy;
import com.esri.arcgis.geodatabase.IField;
import com.esri.arcgis.geodatabase.IFieldEdit;
import com.esri.arcgis.geodatabase.IFields;
import com.esri.arcgis.geodatabase.IFieldsEdit;
import com.esri.arcgis.geodatabase.IGeometryDef;
import com.esri.arcgis.geodatabase.IGeometryDefEdit;
import com.esri.arcgis.geodatabase.IQueryFilter;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.geodatabase.esriFeatureType;
import com.esri.arcgis.geodatabase.esriFieldType;
import com.esri.arcgis.geometry.IGeometry;
import com.esri.arcgis.geometry.IPolygon;
import com.esri.arcgis.geometry.IPolygonProxy;
import com.esri.arcgis.geometry.ITopologicalOperator;
import com.esri.arcgis.geometry.ITopologicalOperatorProxy;
import com.esri.arcgis.geometry.UnknownCoordinateSystem;
import com.esri.arcgis.geometry.esriGeometryType;
import com.esri.arcgis.system.AoInitialize;
import com.esri.arcgis.system.EngineInitializer;
import com.esri.arcgis.system.esriLicenseProductCode;
import com.esri.arcgis.systemUI.esriCommandStyles;

/**
 * This sample demonstrates how to buffer selected features in a layer, and
 * how to display the resulting buffer polygon on a Map. The buffer distance
 * is hard-coded to "1.0", which assumes that the shapefile data for the layer
 * is stored in decimal degrees, for simplicity.
 *
 */

public class BufferFeatures extends JFrame {

  MapControl mapControl = new MapControl();
  ToolbarControl toolbarControl = new ToolbarControl();
  TOCControl toc = new TOCControl();
  JPanel toolBarPanel = new JPanel();

  JButton button = new JButton("Add a shapefile...");
  JButton btnBuffer = new JButton("Buffer selected features");
  IGeometry result = null;
  String _distance = null;

  public BufferFeatures() {
    buildFrame();
  }

  /**This method builds 'this' frame as per the following diagram:
       *
       *   /----------------------------------------------------------\
       *   |            BorderLayout.NORTH                            |
       *   |            Toolbar Control                               |
       *   |--------------|-------------------------------------------|
       *   |              |                                           |
       *   |              |                                           |
       *   |  TocControl  |     MapControl                            |
       *   |  BorderLayout|    BorderLayout.CENTER                    |
       *   |    WEST      |                                           |
       *   |              |                                           |
       *   |              |                                           |
       *   |              |                                           |
       *   |--------------|-------------------------------------------|
   */


  private void buildFrame(){

    getContentPane().setLayout(new java.awt.BorderLayout());
    getContentPane().add(mapControl,java.awt.BorderLayout.CENTER);
    getContentPane().add(toolBarPanel,java.awt.BorderLayout.NORTH);
    getContentPane().add(toc,java.awt.BorderLayout.WEST);
    toolBarPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    toolBarPanel.add(button);
    toolBarPanel.add(btnBuffer);
    toolbarControl.setSize(450,20);
    toc.setSize(150,580);
    toolBarPanel.add(toolbarControl);


    button.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        //Browse to a shapefile and add a FeatureLayer to the map
        addShapefile();
      }
    });

    btnBuffer.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        processBufferButtonAction(evt);
      }
    });


  }

  /**Method to process buffer called when buffer button clicked.
   * @param evt
   */

  public void processBufferButtonAction(ActionEvent evt){
    // Buffer the selected Features...
    BufferUI ui = new BufferUI(this,"Buffer Properties",true);
    ui.setSize(300,250);
    ui.setResizable(false);
    ui.setVisible(true);
  }

  public void doBuffer(String distance){

    _distance = distance;

    try {

       IScreenDisplay pScreenDisplay = mapControl.getActiveView().getScreenDisplay();
       ISimpleFillSymbol pSymbol = new SimpleFillSymbol();

       IRgbColor c = new RgbColor();
       IRgbColor color = new IRgbColorProxy(c);

       color.setRed(255);
       color.setGreen(0);
       color.setBlue(0);

       pSymbol.setColor(color);
       pSymbol.setStyle(esriSimpleFillStyle.esriSFSDiagonalCross);
       ISymbol s = new ISymbolProxy(pSymbol);

       ICursor[] fc = {};
       IQueryFilter qfilter = new QueryFilter();
       qfilter.setWhereClause(" ");
       ISelection selection = mapControl.getActiveView().getFocusMap().getFeatureSelection();
       IEnumFeature ef = new IEnumFeatureProxy(selection);
       IFeature feature = ef.next();
       ITopologicalOperator u = null;

       while(feature != null){

         ITopologicalOperator p = new ITopologicalOperatorProxy(feature.
               getShape());

         IGeometry buffer = p.buffer(Double.parseDouble(distance)); // Assumes a Geographic projection, for simplicity.
         IPolygon poly = new IPolygonProxy(buffer);

         if(u == null){
           u = new ITopologicalOperatorProxy(poly);
         }

         result = u.union(poly);

         feature = ef.next();


       }

       if(result != null){

         pScreenDisplay.startDrawing(0, Short.parseShort("0"));
         pScreenDisplay.setSymbol(s);
         pScreenDisplay.drawPolygon(result);
         pScreenDisplay.finishDrawing();
       }else{
         JOptionPane.showMessageDialog(this,"Please select features first!");
       }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**Method to save shape file
   *
   * @param output
   */

  public void doSaveBuffer(String output){

    java.io.File pFile = new java.io.File(output);

    if(pFile.isDirectory()){
      JOptionPane.showMessageDialog(this,"The specified file is actually a directory. Please enter a shapefile name.");
      return;
    }

    String strFClassName = pFile.getName();

    if(result != null){
      try {

        IWorkspaceFactory factory = new ShapefileWorkspaceFactory();
        IWorkspace ws = factory.openFromFile(pFile.getParent(), 0);
        IFeatureWorkspace pFWS = new IFeatureWorkspaceProxy(ws);

        // Make sure the featureclass doesn't already exist...

        try{
          IFeatureClass pTest = pFWS.openFeatureClass(strFClassName + ".shp");
          if (pTest != null) {
            JOptionPane.showMessageDialog(this,
                strFClassName + " already exists. Please specify a unique shapefile name");
            return;
          }
        }catch(Exception e){}//it's OK. The file doesn't exist

        // Set up the Fields...

        IFields pFields = null;
        IFieldsEdit pFieldsEdit = null;
        pFields = new Fields();
        pFieldsEdit = (IFieldsEdit) pFields;

        IField pField = null;
        IFieldEdit pFieldEdit = null;

        //Make the shape field it will need a geometry definition, with a spatial reference
        pField = new Field();
        pFieldEdit = (IFieldEdit) pField;
        pFieldEdit.setName("Shape");
        pFieldEdit.setType(esriFieldType.esriFieldTypeGeometry);

        IGeometryDef pGeomDef = null;
        IGeometryDefEdit pGeomDefEdit = null;
        pGeomDef = new GeometryDef();
        pGeomDefEdit = (IGeometryDefEdit) pGeomDef;
        pGeomDefEdit.setGeometryType(esriGeometryType.esriGeometryPolygon);
        pGeomDefEdit.setSpatialReferenceByRef(new UnknownCoordinateSystem());

        pFieldEdit.setGeometryDefByRef(pGeomDef);
        pFieldsEdit.addField(pField);

        //Add another miscellaneous text field
        pField = new Field();
        pFieldEdit = (IFieldEdit) pField;
        pFieldEdit.setLength(30);
        pFieldEdit.setName("Scale");
        pFieldEdit.setType(esriFieldType.esriFieldTypeDouble);
        pFieldsEdit.addField(pField);

        //Create the shapefile (some parameters apply to geodatabase options and can be defaulted as Nothing)
        IFeatureClass pFeatClass = null;
        pFeatClass = pFWS.createFeatureClass(strFClassName, pFields, null, null,
                                             esriFeatureType.esriFTSimple,
                                             "Shape", "");


        // Now, add the buffer polygon as a feature
        IFeatureCursor pFC = pFeatClass.IFeatureClass_insert(true);
        IFeatureBuffer pFB = pFeatClass.createFeatureBuffer();

        pFB.setShapeByRef(result);
        int index = pFB.getFields().findField("Scale");
        pFB.setValue(index,new Double(Double.parseDouble(_distance)));
        pFC.insertFeature(pFB);

      }
      catch (Exception e) {
        System.out.println("Error saving shapefile: " + e);

      }
    }

  }

  /**Initializes all the controls
   *
   */

  public void display(){

    try {
      toolbarControl.setBuddyControl(mapControl);
      toc.setBuddyControl(mapControl);

      toolbarControl.addItem(new ControlsSelectFeaturesTool(),
                 0, -1, true, 0, esriCommandStyles.esriCommandStyleIconOnly); //SelectFeatures
      toolbarControl.addItem(new ControlsMapZoomInTool(),
                             0, -1, true, 0, esriCommandStyles.esriCommandStyleIconOnly); //ZoomIn
      toolbarControl.addItem(new ControlsMapZoomOutTool(),
                             0, -1, false, 0, esriCommandStyles.esriCommandStyleIconOnly); //ZoomOut
      toolbarControl.addItem(new ControlsMapPanTool(),
                             0, -1, false, 0, esriCommandStyles.esriCommandStyleIconOnly); //Pan
      // ZoominFixed
      toolbarControl.addItem(new ControlsMapZoomInFixedCommand(),
                 0, -1, false, 0, esriCommandStyles.esriCommandStyleIconOnly);
      // ZoomoutFixed
      toolbarControl.addItem(new ControlsMapZoomOutFixedCommand(),
                 0, -1, false, 0, esriCommandStyles.esriCommandStyleIconOnly);
      //MapZoomPan
      toolbarControl.addItem(new ControlsMapZoomPanTool(),
                 0, -1, false, 0, esriCommandStyles.esriCommandStyleIconOnly);
      // MapFullExtent
      toolbarControl.addItem(new ControlsMapFullExtentCommand(),
                 0, -1, false, 0, esriCommandStyles.esriCommandStyleIconOnly);
      //ZoomToLastExtentBack
      toolbarControl.addItem(new ControlsMapZoomToLastExtentBackCommand(),
                 0, -1, false, 0, esriCommandStyles.esriCommandStyleIconOnly);
      // ZoomToLastExtentForward
      toolbarControl.addItem(new ControlsMapZoomToLastExtentForwardCommand(),
                 0, -1, false, 0, esriCommandStyles.esriCommandStyleIconOnly);
      // Select
      toolbarControl.addItem(new ControlsSelectTool(),
                 0, -1, false, 0, esriCommandStyles.esriCommandStyleIconOnly);


    }catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  /** Diplays file chooser to dialog to add shape file
   *
   */

  private void addShapefile(){

    JFileChooser fc = new JFileChooser();
    fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
      public boolean accept(java.io.File f) {
        if (f.isDirectory())
          return true;
        if (f.getName().endsWith(".shp") || f.getName().endsWith(".SHP"))
          return true; //INN
        return false;
      }

      // The description of this filter
      public String getDescription() {
        return "Shapefiles";
      }
    }

    );

    fc.showOpenDialog(this);

    if(fc.getSelectedFile() != null){
      try {
        IFeatureLayer f = new FeatureLayer();
        IWorkspaceFactory factory = new ShapefileWorkspaceFactory();
        IWorkspace ws = factory.openFromFile(fc.getSelectedFile().getParent(),
                                             0);

        IFeatureWorkspace fws = new IFeatureWorkspaceProxy(ws);
        IFeatureClass fclass = fws.openFeatureClass(fc.getSelectedFile().
            getName());

        f.setFeatureClassByRef(fclass);
        f.setName(fc.getSelectedFile().getName());

        mapControl.getActiveView().getFocusMap().addLayer(f);
        mapControl.getActiveView().refresh();

      }
      catch (Exception ex) {
        System.out.println("Exception in addShapefile...");
        ex.printStackTrace();
      }
    }

  }

  public static void main(String[] args) {

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      EngineInitializer.initializeVisualBeans();
      AoInitialize aoInit = new AoInitialize();
      aoInit.initialize(esriLicenseProductCode.esriLicenseProductCodeEngine);

      BufferFeatures bufferFeatures = new BufferFeatures();
      bufferFeatures.setSize(780,500);
      bufferFeatures.setTitle("BufferFeatures - ArcObjects Java SDK Developer Sample");
      bufferFeatures.setVisible(true);
      bufferFeatures.display();
      bufferFeatures.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  /** Dialog to display buffer attributes.
   *
   */

  class BufferUI extends JDialog {
    JPanel panel1 = new JPanel();
    JSlider jSlider1 = new JSlider();
    JCheckBox jCheckBox1 = new JCheckBox();
    JTextField jTextField1 = new JTextField();
    JButton btnCancel = new JButton();
    JButton btnOK = new JButton();
    JLabel jLabel1 = new JLabel();
    private String _distance;
    private String _shapefilePath;
    private boolean _saveBuffer;

    public BufferUI(Frame frame, String title, boolean modal) {
      super(frame, title, modal);
      try {
        buildUI();
        pack();
      }
      catch(Exception ex) {
        ex.printStackTrace();
      }
    }

    public BufferUI() {
      this(null, "", false);
    }
    private void buildUI() throws Exception {
      panel1.setLayout(null);
      this.getContentPane().setLayout(null);
      panel1.setBounds(new Rectangle(-1, 10, 400, 291));
      jSlider1.setMaximum(10);
      jSlider1.setMinimum(0);
      java.util.Hashtable labelTable = jSlider1.createStandardLabels(10,0);
      jSlider1.setLabelTable(labelTable);
      jSlider1.setMinorTickSpacing(1);
      jSlider1.setPaintLabels(true);
      jSlider1.setPaintTicks(true);
      jSlider1.setMinimumSize(new Dimension(36, 32));
      jSlider1.setBounds(new Rectangle(21, 37, 244, 40));
      jCheckBox1.setToolTipText("");
      jCheckBox1.setText("Save buffer to shapefile");
      jCheckBox1.setBounds(new Rectangle(21, 101, 164, 23));
      jTextField1.setText("jTextField1");
      jTextField1.setBounds(new Rectangle(21, 134, 242, 25));
      jTextField1.setText("C:/temp");
      this.setModal(true);

      // Action for the Cancel button...
      btnCancel.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
          btnCancel_actionPerformed(evt);
        }
      });

      // Action for the OK button...
      btnOK.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
          btnOK_actionPerformed(evt);
        }
      });

      btnCancel.setBounds(new Rectangle(114, 186, 75, 26));
      btnCancel.setText("Cancel");
      btnOK.setBounds(new Rectangle(211, 187, 77, 25));
      btnOK.setText("OK");
      jLabel1.setText("Set buffer distance");
      jLabel1.setBounds(new Rectangle(21, 17, 133, 15));
      panel1.add(jSlider1, null);
      panel1.add(jLabel1, null);
      panel1.add(jCheckBox1, null);
      panel1.add(jTextField1, null);
      panel1.add(btnOK, null);
      panel1.add(btnCancel, null);
      this.getContentPane().add(panel1, null);
    }

    public String getDistance(){
      return new String(jSlider1.getValue()+".0");
    }

    public boolean saveBuffer(){
      return jCheckBox1.isSelected();
    }

    void btnCancel_actionPerformed(ActionEvent e) {
      this.dispose();
    }

    void btnOK_actionPerformed(ActionEvent e) {
      doBuffer(getDistance());

      if(saveBuffer()){
        doSaveBuffer(jTextField1.getText());
      }
      this.dispose();
    }

  }
}