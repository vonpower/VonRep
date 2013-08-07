
/*
 * ArcGIS Engine Developer Sample
 * Application Name: DisplayRenderersMain.java
 */


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.esri.arcgis.carto.ChartRenderer;
import com.esri.arcgis.carto.ClassBreaksRenderer;
import com.esri.arcgis.carto.DotDensityRenderer;
import com.esri.arcgis.carto.FeatureLayer;
import com.esri.arcgis.carto.IChartRenderer;
import com.esri.arcgis.carto.IClassBreaksRenderer;
import com.esri.arcgis.carto.IDotDensityRenderer;
import com.esri.arcgis.carto.IFeatureRenderer;
import com.esri.arcgis.carto.IProportionalSymbolRenderer;
import com.esri.arcgis.carto.IRendererFields;
import com.esri.arcgis.carto.IUniqueValueRenderer;
import com.esri.arcgis.carto.ProportionalSymbolRenderer;
import com.esri.arcgis.carto.SimpleRenderer;
import com.esri.arcgis.carto.UniqueValueRenderer;
import com.esri.arcgis.carto.esriViewDrawPhase;
import com.esri.arcgis.controls.MapControl;
import com.esri.arcgis.display.AlgorithmicColorRamp;
import com.esri.arcgis.display.BarChartSymbol;
import com.esri.arcgis.display.DotDensityFillSymbol;
import com.esri.arcgis.display.HsvColor;
import com.esri.arcgis.display.IAlgorithmicColorRamp;
import com.esri.arcgis.display.IBarChartSymbol;
import com.esri.arcgis.display.IChartSymbol;
import com.esri.arcgis.display.IColor;
import com.esri.arcgis.display.IDotDensityFillSymbol;
import com.esri.arcgis.display.IEnumColors;
import com.esri.arcgis.display.IFillSymbol;
import com.esri.arcgis.display.IHsvColor;
import com.esri.arcgis.display.ILineSymbol;
import com.esri.arcgis.display.IMarkerSymbol;
import com.esri.arcgis.display.IRandomColorRamp;
import com.esri.arcgis.display.IRgbColor;
import com.esri.arcgis.display.ISimpleFillSymbol;
import com.esri.arcgis.display.ISimpleMarkerSymbol;
import com.esri.arcgis.display.ISymbol;
import com.esri.arcgis.display.ISymbolArray;
import com.esri.arcgis.display.ISymbolProxy;
import com.esri.arcgis.display.RandomColorRamp;
import com.esri.arcgis.display.RgbColor;
import com.esri.arcgis.display.SimpleFillSymbol;
import com.esri.arcgis.display.SimpleLineSymbol;
import com.esri.arcgis.display.SimpleMarkerSymbol;
import com.esri.arcgis.display.esriColorRampAlgorithm;
import com.esri.arcgis.display.esriSimpleFillStyle;
import com.esri.arcgis.display.esriSimpleMarkerStyle;
import com.esri.arcgis.geodatabase.DataStatistics;
import com.esri.arcgis.geodatabase.ICursor;
import com.esri.arcgis.geodatabase.IDataStatistics;
import com.esri.arcgis.geodatabase.IQueryFilter;
import com.esri.arcgis.geodatabase.IRow;
import com.esri.arcgis.geodatabase.IRowBuffer;
import com.esri.arcgis.geodatabase.ITable;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.system.EngineInitializer;
import com.esri.arcgis.system.IStatisticsResults;
import com.esri.arcgis.system.esriUnits;

/**
 * This sample shows a standalone Java application that uses the ArcObjects
 * components to load the US states shape file. The main aim is to illustrate
 * some of the different renderer objects that are available by selecting
 * an appropriate button.
 * Application uses the "states" shapefiles from the developer kit samples.
 * If user want to load other shapefile then attribute fieldnames should be
 * changed appropriately. ClassBreaks and UniqueValue renderers use
 * STATE_NAME, POP1990, POP1999 and STATE_FIPS fields.
 */
public class DisplayRenderersMain extends JFrame implements ActionListener {

  String textNameField = "STATE_NAME";
  String textPopField1 = "POP1990";
  String textPopField2 = "POP1999";
  String textStateFips = "STATE_FIPS";

  MapControl mapControl = new MapControl();
  Panel toolPanel = new Panel();
  TextField textField = new TextField();
  Label labelComment = new Label();
  Button buttonOpenFile = new Button();
  Button buttonSimpleRendererButton = new Button();
  Button buttonUniqueValueButton = new Button();
  Button buttonClassBreaksButton = new Button();
  Button buttonProportionalButton = new Button();
  Button buttonBarChartButton = new Button();
  Button buttonDotDensityButton = new Button();
  FeatureLayer featureLayer = null;

  /**
   * Default constructor.
   */
  public DisplayRenderersMain() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to start the program execution.
   */
  public static void main(String s[]) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      EngineInitializer.initializeVisualBeans();
      DisplayRenderersMain application = new DisplayRenderersMain();
      application.show();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * This is a private method created by UI JBuilder design.
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    this.setTitle("DisplayRenderers Java Sample: Simple, UniqueValue, ClassBreak, Proportional, BarChart, DotDensity renderers");
    this.setSize(new Dimension(512, 512));
    this.getContentPane().setLayout(new BorderLayout());

    // turn on if you like to design in JBuilder
    //this.getContentPane().setLayout(null);
    //mapControl.setBounds(new Rectangle(7, 8, 487, 365));

    labelComment.setText("Open developerkit/samples/data/usa/states.shp file.");
    labelComment.setBounds(new Rectangle(12, 5, 265, 26));
    buttonOpenFile.setLabel("Open file ...");
    buttonOpenFile.setBounds(new Rectangle(383, 4, 91, 25));

    buttonSimpleRendererButton.setLabel("Simple");
    buttonSimpleRendererButton.setBounds(new Rectangle(9, 40, 90, 25));
    buttonUniqueValueButton.setLabel("Unique Value");
    buttonUniqueValueButton.setBounds(new Rectangle(109, 40, 104, 25));
    buttonClassBreaksButton.setLabel("Class Breaks");
    buttonClassBreaksButton.setBounds(new Rectangle(222, 40, 103, 25));
    buttonProportionalButton.setLabel("Proportional");
    buttonProportionalButton.setBounds(new Rectangle(110, 71, 103, 25));
    buttonBarChartButton.setLabel("BarChart");
    buttonBarChartButton.setBounds(new Rectangle(223, 71, 103, 25));
    buttonDotDensityButton.setLabel("DotDensity");
    buttonDotDensityButton.setBounds(new Rectangle(9, 71, 90, 25));

    toolPanel.setBackground(new Color(212, 210, 177));
    toolPanel.setBounds(new Rectangle(7, 382, 489, 109));
    toolPanel.setLayout(null);
    toolPanel.add(textField, null);
    toolPanel.add(buttonOpenFile, null);
    toolPanel.add(buttonUniqueValueButton, null);
    toolPanel.add(buttonSimpleRendererButton, null);
    toolPanel.add(buttonClassBreaksButton, null);
    toolPanel.add(buttonBarChartButton, null);
    toolPanel.add(buttonDotDensityButton, null);
    toolPanel.add(buttonProportionalButton, null);
    toolPanel.add(labelComment, null);

    this.getContentPane().add(mapControl, BorderLayout.CENTER);
    this.getContentPane().add(toolPanel, BorderLayout.SOUTH);
    this.setVisible(true);

    buttonOpenFile.addActionListener(this);
    buttonSimpleRendererButton.addActionListener(this);
    buttonUniqueValueButton.addActionListener(this);
    buttonClassBreaksButton.addActionListener(this);
    buttonProportionalButton.addActionListener(this);
    buttonBarChartButton.addActionListener(this);
    buttonDotDensityButton.addActionListener(this);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

  }

  /**
   * @see ActionListener#actionPerformed
   * @param event
   */
  public void actionPerformed(ActionEvent event) {
    Object evt = event.getSource();
    if (evt == buttonOpenFile) {
      try {
        FileDialog fileDialog = new FileDialog(this, "Open File", FileDialog.LOAD);
        fileDialog.show();
        String fullFilename = fileDialog.getDirectory() + fileDialog.getFile();
        String path = fullFilename.substring(0, fullFilename.lastIndexOf(File.separator));
        String name = fullFilename.substring(fullFilename.lastIndexOf(File.separator) + 1);
        mapControl.clearLayers();
        mapControl.addShapeFile(path, name);
        featureLayer = new FeatureLayer(mapControl.getLayer(0));
        mapControl.refresh(esriViewDrawPhase.esriViewGeography, null, null);
        System.out.println("Open file");
      } catch (java.lang.Exception ex) {
        System.out.println("Can't open file");
      }
    }
    if (evt == buttonSimpleRendererButton) {
      simpleRenderer();
    }
    if (evt == buttonUniqueValueButton) {
      uniqueValueRenderer();
    }
    if (evt == buttonClassBreaksButton) {
      classBreaksRenderer();
    }
    if (evt == buttonProportionalButton) {
      proportionalRenderer();
    }
    if (evt == buttonBarChartButton) {
      barChartRenderer();
    }
    if (evt == buttonDotDensityButton) {
      dotDensityRenderer();
    }
  }

  /**
   * The method creates SimpleRenderer object and attach it to the map.
   */
  private void simpleRenderer() {
    try {
      SimpleFillSymbol pSimpleFillSymbol = new SimpleFillSymbol();
      RgbColor pColor = new RgbColor();
      pColor.setRed(213);
      pColor.setGreen(212);
      pColor.setBlue(252);
      pSimpleFillSymbol.setColor(pColor);
      ILineSymbol outlineSymbol = new SimpleLineSymbol();
      outlineSymbol.setColor(new RgbColor()); // 0,0,0
      outlineSymbol.setWidth(1.0);
      pSimpleFillSymbol.setOutline(outlineSymbol);
      SimpleRenderer simpleRenderer = new SimpleRenderer();
      simpleRenderer.setSymbolByRef(pSimpleFillSymbol);
      FeatureLayer featureLayer = new FeatureLayer(mapControl.getLayer(0));
      featureLayer.setRendererByRef(simpleRenderer);
      mapControl.refresh(esriViewDrawPhase.esriViewBackground, null, null); // esriViewGeography doens't work
      System.out.println("Simple Renderer");
    } catch (java.lang.Exception ex) {
      System.out.println("Simple Renderer Exception:" + ex);
    }
  }

  /**
   * The method creates UniqueValueRenderer object and attach it to the map.
   */
  private void uniqueValueRenderer() {
    try {
      IUniqueValueRenderer pUniqueValueRenderer = new UniqueValueRenderer();
      ITable pTable = featureLayer;
      int fieldNumber = pTable.findField(textNameField);
      if (fieldNumber == -1) {
        System.out.println("Can't find field : " + textNameField);
        return;
      }
      pUniqueValueRenderer.setFieldCount(1);
      pUniqueValueRenderer.setField(0, textNameField);
      IRandomColorRamp pColorRamp = new RandomColorRamp();
      pColorRamp.setStartHue(0);
      pColorRamp.setMinValue(99);
      pColorRamp.setMaxSaturation(15);
      pColorRamp.setEndHue(360);
      pColorRamp.setMaxValue(100);
      pColorRamp.setMaxSaturation(30);
      pColorRamp.setSize(100);
      boolean rampBool[] = new boolean[1];
      rampBool[0] = true;
      pColorRamp.createRamp(rampBool);
      IEnumColors pEnumRamp = pColorRamp.getColors();
      IQueryFilter pQueryFilter = new QueryFilter();
      pQueryFilter.addField(textNameField);

      ICursor pCursor = pTable.ITable_search(pQueryFilter, true);
      IRow pNextRow = pCursor.nextRow();

      while (pNextRow != null) {
        IRow pNextRowBuffer = pNextRow;
        Object codeValue = pNextRowBuffer.getValue(fieldNumber);
        IColor pNextUniqueColor = pEnumRamp.next();
        if (pNextUniqueColor == null) {
          pEnumRamp.reset();
          pNextUniqueColor = pEnumRamp.next();
        }
        IFillSymbol pSym = new SimpleFillSymbol();
        int k=pNextUniqueColor.getRGB();
        System.out.println(k);
        pSym.setColor(pNextUniqueColor);
        System.out.println("addvalue:"+codeValue+","+codeValue);
        pUniqueValueRenderer.addValue((String) codeValue, (String) codeValue, (ISymbol) pSym);
        pNextRow = pCursor.nextRow();
      }
      featureLayer.setRendererByRef((IFeatureRenderer) pUniqueValueRenderer);
      mapControl.refresh(esriViewDrawPhase.esriViewGeography, null, null);
      System.out.println("Unique Value Renderer");
    } catch (java.lang.Exception ex) {
      System.out.println("Unique Value Renderer:" + ex);
    }
  }

  /**
   * The method creates ClassBreaksRenderer object and attach it to the map.
   */
  private void classBreaksRenderer() {
    try {
      double classes[] = new double[3];
      classes[0] = 1;
      classes[1] = 20;
      classes[2] = 56;

      IClassBreaksRenderer pClassBreaksRenderer = new ClassBreaksRenderer();
      pClassBreaksRenderer.setField(textStateFips);
      pClassBreaksRenderer.setBreakCount(3);
      pClassBreaksRenderer.setSortClassesAscending(true);
      IHsvColor pFromColor = new HsvColor();
      pFromColor.setHue(60);         // Yellow
      pFromColor.setSaturation(100);
      pFromColor.setValue(96);
      IHsvColor pToColor = new HsvColor();
      pToColor.setHue(0);         // Red
      pToColor.setSaturation(100);
      pToColor.setValue(96);
      IAlgorithmicColorRamp pRamp = new AlgorithmicColorRamp();
      pRamp.setAlgorithm(esriColorRampAlgorithm.esriHSVAlgorithm);
      pRamp.setFromColor(pFromColor);
      pRamp.setToColor(pToColor);
      pRamp.setSize(3);
      boolean ok[] = new boolean[1];
      pRamp.createRamp(ok);
      IEnumColors pEnumColors = pRamp.getColors();

      for (int breakIndex = 0; breakIndex < 3; breakIndex++) {
        IColor pColor = pEnumColors.next();
        ISimpleFillSymbol pFillSymbol = new SimpleFillSymbol();
        pFillSymbol.setColor(pColor);
        pFillSymbol.setStyle(esriSimpleFillStyle.esriSFSSolid);
        pClassBreaksRenderer.setSymbol(breakIndex, new ISymbolProxy(pFillSymbol));
        pClassBreaksRenderer.setBreak(breakIndex, classes[breakIndex]);
      }
      featureLayer.setRendererByRef((IFeatureRenderer) pClassBreaksRenderer);
      mapControl.refresh(esriViewDrawPhase.esriViewGeography, null, null);
      System.out.println("Class Breaks Renderer");
    } catch (java.lang.Exception ex) {
      System.out.println("Class Breaks Renderer Exception:" + ex);
    }
  }

  /**
   * The method creates ProportionalRenderer object and attach it to the map.
   */
  private void proportionalRenderer() {
    try {
      ITable pTable = featureLayer;
      IQueryFilter pQueryFilter = new QueryFilter();
      pQueryFilter.addField(textPopField1);
      ICursor pCursor = pTable.ITable_search(pQueryFilter, true);
      IDataStatistics pDataStatistics = new DataStatistics();
      pDataStatistics.setCursorByRef(pCursor);
      pDataStatistics.setField(textPopField1);
      IStatisticsResults pStatisticsResult = pDataStatistics.getStatistics();
      if (pStatisticsResult == null) {
        System.out.println("Failed to gather stats on the feature class");
        return;
      }
      IFillSymbol pFillSymbol = new SimpleFillSymbol();
      pFillSymbol.setColor(getRGBColor(239, 228, 190)); // Tan
      ISimpleMarkerSymbol pSimpleMarkerSymbol = new SimpleMarkerSymbol();
      pSimpleMarkerSymbol.setStyle(esriSimpleMarkerStyle.esriSMSSquare);
      pSimpleMarkerSymbol.setColor(getRGBColor(255, 0, 0));  // Red
      pSimpleMarkerSymbol.setSize(2);
      pSimpleMarkerSymbol.setOutline(true);
      pSimpleMarkerSymbol.setOutlineColor(getRGBColor(0, 0, 0)); // Black
      IProportionalSymbolRenderer pProportionalSymbolRenderer = new ProportionalSymbolRenderer();
      pProportionalSymbolRenderer.setValueUnit(esriUnits.esriUnknownUnits);
      pProportionalSymbolRenderer.setField(textPopField1);
      pProportionalSymbolRenderer.setFlanneryCompensation(false);
      pProportionalSymbolRenderer.setMinDataValue(pStatisticsResult.getMinimum());
      pProportionalSymbolRenderer.setMaxDataValue(pStatisticsResult.getMaximum());
      pProportionalSymbolRenderer.setBackgroundSymbol(pFillSymbol);
      pProportionalSymbolRenderer.setMinSymbol((ISymbol) pSimpleMarkerSymbol);
      featureLayer.setRendererByRef((IFeatureRenderer) pProportionalSymbolRenderer);
      mapControl.refresh(esriViewDrawPhase.esriViewGeography, null, null);
      System.out.println("Proportional Renderer");
    } catch (java.lang.Exception ex) {
      System.out.println("Proportional Renderer Exception:" + ex);
    }
  }

  /**
   * The method creates BarChartRenderer object and attach it to the map.
   */
  private void barChartRenderer() {
    try {
      IChartRenderer pChartRenderer = new ChartRenderer();
      IRendererFields pRendererFields = (IRendererFields) pChartRenderer;
      pRendererFields.addField(textPopField1, null);
      pRendererFields.setFieldAlias(0, pRendererFields.getField(0));
      pRendererFields.addField(textPopField2, null);
      pRendererFields.setFieldAlias(1, pRendererFields.getField(1));

      ITable pTable = featureLayer;
      IQueryFilter pQueryFilter = new QueryFilter();
      pQueryFilter.addField(textPopField1);
      pQueryFilter.addField(textPopField2);
      ICursor pCursor = pTable.ITable_search(pQueryFilter, true);
      int numFields = 2; // Number of bars
      int[] fieldIndecies = new int[numFields];
      fieldIndecies[0] = pTable.findField(textPopField1);
      fieldIndecies[1] = pTable.findField(textPopField2);
      double maxValue = 0;
      boolean firstValue = true;
      IRowBuffer pRow = pCursor.nextRow();
      while (pRow != null) {
        for (int fieldIndex = 0; fieldIndex < numFields; fieldIndex++) {
          double fieldValue = Double.parseDouble(pRow.getValue(fieldIndecies[fieldIndex]).toString());
          if (firstValue) {
            maxValue = fieldValue;
            firstValue = false;
          }
          if (fieldValue > maxValue)
            maxValue = fieldValue;
        }
        pRow = pCursor.nextRow();
      }
      if (maxValue <= 0) {
        System.out.println("Failed to calculate the maximum value or max value is 0.");
        return;
      }

      IBarChartSymbol pBarChartSymbol = new BarChartSymbol();
      IChartSymbol pChartSymbol = (IChartSymbol) pBarChartSymbol;
      pBarChartSymbol.setWidth(6);
      IMarkerSymbol pMarkerSymbol = (IMarkerSymbol) pBarChartSymbol;
      pChartSymbol.setMaxValue(maxValue);
      pMarkerSymbol.setSize(16);

      ISymbolArray pSymbolArray = (ISymbolArray) pBarChartSymbol;
      IFillSymbol pFillSymbol1 = new SimpleFillSymbol();
      pFillSymbol1.setColor(getRGBColor(213, 212, 252));
      pSymbolArray.addSymbol((ISymbol) pFillSymbol1);
      IFillSymbol pFillSymbol2 = new SimpleFillSymbol();
      pFillSymbol2.setColor(getRGBColor(193, 252, 179));
      pSymbolArray.addSymbol((ISymbol) pFillSymbol2);

      pChartRenderer.setChartSymbolByRef((IChartSymbol) pBarChartSymbol);
      pChartRenderer.setLabel("Population");
      SimpleFillSymbol pFillSymbol3 = new SimpleFillSymbol();
      pFillSymbol3.setColor(getRGBColor(239, 228, 190));
      pChartRenderer.setBaseSymbolByRef(pFillSymbol3);
      pChartRenderer.setUseOverposter(false);

      featureLayer.setRendererByRef((IFeatureRenderer) pChartRenderer);
      mapControl.refresh(esriViewDrawPhase.esriViewGeography, null, null);

      System.out.println("BarChart Renderer");
    } catch (java.lang.Exception ex) {
      System.out.println("BarChart Renderer Exception : " + ex);
    }
  }

  /**
   * The method creates DotDensityRenderer object and attach it to the map.
   */
  private void dotDensityRenderer() {
    try {
      IDotDensityRenderer pDotDensityRenderer = new DotDensityRenderer();
      IRendererFields pRendererFields = (IRendererFields) pDotDensityRenderer;
      pRendererFields.addField(textPopField1, null);
      IDotDensityFillSymbol pDotDensityFillSymbol = new DotDensityFillSymbol();
      pDotDensityFillSymbol.setDotSize(3);
      pDotDensityFillSymbol.setColor(getRGBColor(0, 0, 0));
      pDotDensityFillSymbol.setBackgroundColor(getRGBColor(239, 228, 190)); // tan
      ISymbolArray pSymbolArray = (ISymbolArray) pDotDensityFillSymbol;
      ISimpleMarkerSymbol pMarkerSymbol = new SimpleMarkerSymbol();
      pMarkerSymbol.setStyle(esriSimpleMarkerStyle.esriSMSCircle);
      pMarkerSymbol.setSize(3);
      pMarkerSymbol.setColor(getRGBColor(0, 0, 0)); // Black
      pSymbolArray.addSymbol((ISymbol) pMarkerSymbol);
      pDotDensityRenderer.setDotDensitySymbolByRef(pDotDensityFillSymbol);
      pDotDensityRenderer.setDotValue(200000);
      featureLayer.setRendererByRef((IFeatureRenderer) pDotDensityRenderer);
      mapControl.refresh(esriViewDrawPhase.esriViewGeography, null, null);
      System.out.println("Dot Density Renderer");
    } catch (java.lang.Exception ex) {
      System.out.println("Dot Density Renderer Exception:" + ex);
    }
  }

  /**
   * The method creates color object from color components.
   * @param red
   * @param green
   * @param blue
   * @return
   */
  private IRgbColor getRGBColor(int red, int green, int blue) {
    IRgbColor rgbColor = null;
    try {
      //Create rgb color and grab hold of the IRGBColor interface
      rgbColor = new RgbColor();
      rgbColor.setRed(red);
      rgbColor.setGreen(green);
      rgbColor.setBlue(blue);
      rgbColor.setUseWindowsDithering(true);
    } catch (Exception ex) {
      System.out.println("Error in getRGBFunction :" + ex);
      ex.printStackTrace();
    }
    return rgbColor;
  }


}

