package ynugis.ui.confDialog;

/*
 * ArcGIS Engine Developer Sample
 * Application Name: PrintPageLayout.java
 */


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import com.esri.arcgis.carto.esriPageFormID;
import com.esri.arcgis.controls.PageLayoutControl;
import com.esri.arcgis.controls.esriControlsMousePointer;
import com.esri.arcgis.output.IPrinter;
import com.esri.arcgis.system.AoInitialize;
import com.esri.arcgis.system.EngineInitializer;
import com.esri.arcgis.system.esriLicenseProductCode;

/**
 * Description: This sample demonstrates changing the PageLayoutControl's page properties and printing the
 * page to the system default printer. The file chooser dialog
 * allows users to search and select map documents, which are validated and loaded into the
 * PageLayoutControl using the CheckMxFile and LoadMxFile methods. Whenever the FormID,
 * Orientation or PrinterToPageMapping properties of the Page are changed the number of pages to be
 * printed is updated using the PrinterPageCount property. Before the PageLayout is sent to the printer
 * using the Print method, the orienataion of the Printer's paper is aligned to that of the Page's
 * paper.
 *
 */


public class PrintPageLayout extends JFrame implements ActionListener, FocusListener {

   JPanel topPanel = null;
   JPanel mainPanel = null;
   JPanel rightPanel = null;
   JPanel pagePanel = null;
   JPanel printerPanel = null;
   JPanel printPanel = null;

   JButton loadDocumentButton = null;
   JTextField pathField = null;
   JLabel pageSizeLabel = null;
   JComboBox pageSizeCombo = null;
   JLabel pageToPrinterLabel = null;
   JComboBox pageToPrinterCombo = null;
   JRadioButton portraitCheck = null;
   JRadioButton landScapeCheck = null;
   ButtonGroup buttonGroup = null;
   JLabel pageCountLabel = null;
   JLabel pageCountLabelValue = null;
   JLabel printerNameLabel = null;
   JLabel printerNameLabelValue = null;
   JLabel paperSizeLabel = null;
   JLabel paperSizeLabelValue = null;
   JLabel paperOrientationLabel = null;
   JLabel paperOrientationLabelValue = null;
   JLabel overLapPagesLabel = null;
   JTextField overLapTextField = null;
   JLabel pagesLabel = null;
   JTextField sartPageTextField = null;
   JLabel toLabel = null;
   JTextField endPageTextField = null;
   JButton printButton = null;

   PageLayoutControl pageLayoutControl;

   public PrintPageLayout() {
      super("Print pageLayout");
      buildFrame();
      setSize(650, 450);
      setVisible(true);
      initControl();
   }

   /**This method builds 'this' frame as per the following diagram:
       *
       *   /-----------------------------------------------------------------------------------\
       *   |            BorderLayout.NORTH                                                     |
       *   |                                                                                   |
       *   |  JTextField    JButton                                                            |
       *   |-----------------------------------------------------------------------------------|
       *   |                                       |  BorderLayout.EAST                        |
       *   |                                       |                                           |
       *   |                                       |                                           |
       *   |                                       | |-----------JPanel:BoxLayout--------------|
       *   |                                       | |                                         |
       *   |                                       | |                                         |
       *   |                                       | |  |------------JPanel:GridLayout---------|
       *   |                                       | |  |    JLabel                            |
       *   |                                       | |  |    JComboBox                         |
       *   |       BorderLayout.CENTER             | |  |    JLabel                            |
       *   |       PageLayoutControl               | |  |    JComboBox                         |
       *   |                                       | |  |    JRadioButton JRadioButton         |
       *   |                                       | |  |    JLabel JLabel                     |
       *   |                                       | |  |--------------------------------------|
       *   |                                       | |  |    JPanel:GridLayout                 |
       *   |                                       | |  |                                      |
       *   |                                       | |  |    JLabel JLabel                     |
       *   |                                       | |  |    JLabel JLabel                     |
       *   |                                       | |  |    JLabel JLabel                     |
       *   |                                       | |  |---------------------------------------
       *                                           | |  |             JPanel: GridLayout       |
       *   |                                       | |  |   JLabel JTextField                  |
       *   |                                       | |  |   JLabel JTextField JLabel JTextField|
       *   |                                       | |  |    JButton                           |
       *   |                                       | |  |                                      |
       *   |-----------------------------------------------------------------------------------|
   */


   public void buildFrame() {
      topPanel = new JPanel();
      mainPanel = new JPanel();
      rightPanel = new JPanel();
      pagePanel = new JPanel();
      printerPanel = new JPanel();
      printPanel = new JPanel();

      topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
      mainPanel.setLayout(new BorderLayout(5, 0));
      rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
      pagePanel.setLayout(new GridLayout(6, 1));
      printerPanel.setLayout(new GridLayout(3, 2));
      printPanel.setLayout(new GridLayout(3, 1, 0, 5));

      loadDocumentButton = new JButton("Load Document");
      loadDocumentButton.addActionListener(this);

      pathField = new JTextField();
      pathField.setEditable(false);
      topPanel.add(pathField);
      topPanel.add(Box.createHorizontalStrut(5));
      topPanel.add(loadDocumentButton);
      topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

      pageSizeLabel = new JLabel("Page Size:");
      pageSizeCombo = new JComboBox();
      pageSizeCombo.addActionListener(this);
      pageToPrinterLabel = new JLabel("Page to Printer Mapping:");
      pageToPrinterCombo = new JComboBox();
      pageToPrinterCombo.addActionListener(this);

      JPanel checkBoxPanel = new JPanel(new BorderLayout());
      portraitCheck = new JRadioButton("Portrait");
      portraitCheck.addActionListener(this);

      landScapeCheck = new JRadioButton("Landscape");
      landScapeCheck.addActionListener(this);

      buttonGroup = new ButtonGroup();
      buttonGroup.add(portraitCheck);
      buttonGroup.add(landScapeCheck);

      checkBoxPanel.add(portraitCheck, BorderLayout.WEST);
      checkBoxPanel.add(landScapeCheck, BorderLayout.EAST);

      JPanel pageCountLabelPanel = new JPanel(new BorderLayout());
      pageCountLabel = new JLabel("Page Count:");
      pageCountLabelValue = new JLabel();
      pageCountLabelPanel.add(pageCountLabel, BorderLayout.WEST);
      pageCountLabelPanel.add(pageCountLabelValue, BorderLayout.EAST);

      pagePanel.add(pageSizeLabel);
      pagePanel.add(pageSizeCombo);
      pagePanel.add(pageToPrinterLabel);
      pagePanel.add(pageToPrinterCombo);
      pagePanel.add(checkBoxPanel);
      pagePanel.add(pageCountLabelPanel);
      pagePanel.setPreferredSize(new Dimension(200, pagePanel.getMinimumSize().height));
      pagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Page"));

      printerNameLabel = new JLabel("Name:");
      printerNameLabelValue = new JLabel();
      paperSizeLabel = new JLabel("Paper Size:");
      paperSizeLabelValue = new JLabel();
      paperSizeLabelValue = new JLabel();
      paperOrientationLabel = new JLabel("Paper Orientation");
      paperOrientationLabelValue = new JLabel();
      printerPanel.add(printerNameLabel);
      printerPanel.add(printerNameLabelValue);
      printerPanel.add(paperSizeLabel);
      printerPanel.add(paperSizeLabelValue);
      printerPanel.add(paperOrientationLabel);
      printerPanel.add(paperOrientationLabelValue);
      printerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Printer"));

      JPanel overLapLabelPanel = new JPanel(new BorderLayout());
      overLapPagesLabel = new JLabel("Overlap between pages ");
      overLapTextField = new JTextField("0");
      overLapTextField.addFocusListener(this);
      overLapLabelPanel.add(overLapPagesLabel, BorderLayout.WEST);
      overLapLabelPanel.add(overLapTextField, BorderLayout.CENTER);
      overLapLabelPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

      JPanel pagesPanel = new JPanel(new GridLayout(1, 4));
      pagesLabel = new JLabel("Pages ");
      sartPageTextField = new JTextField("1");
      toLabel = new JLabel("  To");
      endPageTextField = new JTextField("0");
      pagesPanel.add(pagesLabel);
      pagesPanel.add(sartPageTextField);
      pagesPanel.add(toLabel);
      pagesPanel.add(endPageTextField);
      pagesPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

      printButton = new JButton("Print Page Layout");
      printButton.addActionListener(this);

      printPanel.add(overLapLabelPanel);
      printPanel.add(pagesPanel);
      printPanel.add(printButton);
      printPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Print"));

      rightPanel.add(pagePanel);
      rightPanel.add(printerPanel);
      rightPanel.add(printPanel);

      //Create page layout control add it to the center of the main panel.
      pageLayoutControl = new PageLayoutControl();
      mainPanel.add(topPanel, BorderLayout.NORTH);
      mainPanel.add(pageLayoutControl, BorderLayout.CENTER);
      mainPanel.add(rightPanel, BorderLayout.EAST);
      mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      getContentPane().add(mainPanel, BorderLayout.CENTER);

   }

   /* Intializes control
    */
   public void initControl() {
      //Add esriPageFormID constants to drop down
         pageSizeCombo.addItem("Letter - 8.5in x 11in. ");
         pageSizeCombo.addItem("Legal - 8.5in x 14in.");
         pageSizeCombo.addItem("Tabloid - 11in x 17in.");
         pageSizeCombo.addItem("C - 17in x 22in.");
         pageSizeCombo.addItem("D - 22in x 34in.");
         pageSizeCombo.addItem("E - 34in x 44in.");
         pageSizeCombo.addItem("A5 - 148mm x 210mm.");
         pageSizeCombo.addItem("A4 - 210mm x 297mm.");
         pageSizeCombo.addItem("A3 - 297mm x 420mm.");
         pageSizeCombo.addItem("A2 - 420mm x 594mm.");
         pageSizeCombo.addItem("A1 - 594mm x 841mm.");
         pageSizeCombo.addItem("A0 - 841mm x 1189mm.");
         pageSizeCombo.addItem("Custom Page Size.");
         pageSizeCombo.addItem("Same as Printer Form.");
         pageSizeCombo.setSelectedIndex(7);

         //Add esriPageToPrinterMapping constants to drop down
         pageToPrinterCombo.addItem("0: Crop");
         pageToPrinterCombo.addItem("1: Scale");
         pageToPrinterCombo.addItem("2: Tile");
         //Display printer details
         updatePrintingDisplay();
   }

   /**@see java.awt.event.ActionListener#actionPerformed(ActionEvent event)
   * @param event
   */

  public void actionPerformed(ActionEvent event) {
     if(event.getSource() == loadDocumentButton) {
        try {
               loadFile();

               //Update page display
               pageSizeCombo.setSelectedIndex(pageLayoutControl.getPage().getFormID());
               pageToPrinterCombo.setSelectedIndex(pageLayoutControl.getPage().getPageToPrinterMapping());
               if(pageLayoutControl.getPage().getOrientation() == 1)
                  portraitCheck.setSelected(true);
               else
                  landScapeCheck.setSelected(true);

               //Zoom to whole page
               pageLayoutControl.zoomToWholePage();
               //Update printer page display
               updatePrintPageDisplay();

            }
            catch (IOException ex) {
               System.out.println("Exception in loadDocumentButton#actionPerformed: " + ex);
               ex.printStackTrace();
            }
     }

     if(event.getSource() == pageSizeCombo) {
        try {
            //Set the page size
            pageLayoutControl.getPage().setFormID(pageSizeCombo.
                getSelectedIndex());
            //Update printer page display
            updatePrintPageDisplay();
            }
            catch (IOException ex) {
               System.out.println("Exception in pageSizeCombo#actionPerformed" + ex);
               ex.printStackTrace();
            }

     }


    if(event.getSource() == pageToPrinterCombo) {
       try {
          //Set the page size
          pageLayoutControl.getPage().setPageToPrinterMapping(pageToPrinterCombo.
              getSelectedIndex());
          //Update printer page display
          updatePrintPageDisplay();
       }
       catch (IOException ex) {
          System.out.println("Exception in pageToPrinterCombo#actionPerformed" + ex);
          ex.printStackTrace();
       }

     }

     if(event.getSource() == portraitCheck) {
        try {
            //Set the page orientation
            if(pageLayoutControl.getPage().getFormID() != esriPageFormID.esriPageFormSameAsPrinter)
               pageLayoutControl.getPage().setOrientation((short)1);
            //Update printer page display
            updatePrintPageDisplay();

          }
          catch (IOException ex) {
             System.out.println("Exception in portraitCheck#actionPerformed" + ex);
             ex.printStackTrace();
          }

    }


    if(event.getSource() == landScapeCheck) {
       try {
            //Set the page orientation
            if(pageLayoutControl.getPage().getFormID() != esriPageFormID.esriPageFormSameAsPrinter)
               pageLayoutControl.getPage().setOrientation((short)2);
            //Update printer page display
            updatePrintPageDisplay();

          }
          catch (IOException ex) {
             System.out.println("Exception in landScapeCheck#actionPerformed" + ex);
             ex.printStackTrace();
          }

    }



    if(event.getSource() == printButton) {
       try {
         if(pageLayoutControl.getPrinter() != null)
         //Set mouse pointer
         pageLayoutControl.setMousePointer(esriControlsMousePointer.
                                          esriPointerHourglass);

        //Get IPrinter interface through the PageLayoutControl's printer
        IPrinter printer = pageLayoutControl.getPrinter();
        //Determine whether printer paper's orientation needs changing
        if(printer.getPaper().getOrientation() != pageLayoutControl.getPage().getOrientation()) {
           printer.getPaper().setOrientation(pageLayoutControl.getPage().getOrientation());
           //Update the display
           updatePrintingDisplay();
        }

        //Print the page range with the specified overlap
        Short pageStart = new Short(sartPageTextField.getText());
        Short pageEnd = new Short(endPageTextField.getText());
        Double overLap = new Double(overLapTextField.getText());
        pageLayoutControl.printPageLayout(pageStart.shortValue(), pageEnd.shortValue(), overLap.doubleValue());
        //Set the mouse pointer
        pageLayoutControl.setMousePointer(esriControlsMousePointer.esriPointerDefault);
       }
      catch (IOException ex) {
         System.out.println("Exception in printButton#actionPerformed" + ex);
         ex.printStackTrace();
      }

    }
  }

  /**@see java.awt.event.FocusListener#actionPerformed(FocusEvent event)
   * @param event
   */

  public void focusGained(FocusEvent event) {
     if(event.getSource() == overLapTextField) {
        //Update printer page display
        updatePrintPageDisplay();
     }
  }

  public void focusLost(FocusEvent event) {

  }



   /** Method which determines start and ene pages using overlap text field value
    * and control's page count.
    */

   public void updatePrintPageDisplay() {
      try {
         //Determine the number of pages
         int pageCount;
         Double value = new Double(overLapTextField.getText());
         pageCount = pageLayoutControl.getPrinterPageCount(value.doubleValue());
         Integer pageCountInt = new Integer(pageCount);
         pageCountLabelValue.setText(pageCountInt.toString());
         //Validate start and end pages
         int pageStart, pageEnd;
         pageStart = new Integer(sartPageTextField.getText()).intValue();
         pageEnd = new Integer(endPageTextField.getText()).intValue();
         if (pageStart < 1 || pageStart > pageCount) {
            sartPageTextField.setText("1");
         }
         if (pageEnd < 1 || pageEnd > pageCount) {
            endPageTextField.setText(pageCountInt.toString());
         }
      }
      catch (IOException ex) {
         System.out.println("Exception in updatePrintingDisplay " + ex);
         ex.printStackTrace();
      }
      catch (NumberFormatException ex) {
         System.out.println("Exception in updatePrintPageDisplay " + ex);
         ex.printStackTrace();
      }

   }

   /** Updates paper orienation, printer name and paper size using the printer interface
    *  of pagelayout control.
    *
    */

   private void updatePrintingDisplay() {
      try {
         if (pageLayoutControl.getPrinter() != null) {
            //Get IPrinter interface through the PageLayoutControl's printer
            IPrinter printer = pageLayoutControl.getPrinter();
            //Determine the orientation of the printer's paper
            if(printer.getPaper().getOrientation() == 1)
               paperOrientationLabelValue.setText("Portrait");
            else
               paperOrientationLabelValue.setText("Landscape");
            //Determine the printer name
            printerNameLabelValue.setText(printer.getPaper().getPrinterName());
            //Determine the printer's paper size
            double width [] = new double[1] , height[]  = new double[1];
            printer.getPaper().queryPaperSize(width, height);
            //Format to three decimals
            DecimalFormat format = new DecimalFormat("###.000");
            String widthStr = format.format(width[0]);
            String heightStr = format.format(height[0]);
            paperSizeLabelValue.setText(widthStr + " by " + heightStr + " Inches ");

         }
      }
      catch (IOException ex) {
         System.out.println("Exception in updatePrintingDisplay " + ex);
      }


   }

   /**
    * Method loadFile loads the specified mxd file.
    * Once the file has been loaded, it sets the statusbar text to show the
    * document filename, and sets the document comment in the docCommentPane.
    */
  public boolean loadFile() throws IOException {
       //Open a JFileChooser for selecting map documents
     JFileChooser chooser = new JFileChooser();
     chooser.setFileFilter(new FileFilter() {
        public boolean accept(File f) {
           return (f.isDirectory() ||
                   f.getAbsolutePath().toUpperCase().endsWith(".MXD"));
        }

        public String getDescription() {
           return "Map Documents(*.mxd)";
        }
     });

     boolean loaded = false;
     int returnVal = 0;
     returnVal = chooser.showOpenDialog(null);
     if (returnVal == JFileChooser.APPROVE_OPTION) {
        String fileChosen =
            chooser.getCurrentDirectory()
            + File.separator
            + chooser.getSelectedFile().getName();
        System.out.println("File picked: [" + fileChosen + "]");
         //Check if the selected document can be loaded into control
         //Update the textfiled with the file name.
         pathField.setText(fileChosen);
         //Validate and load map document
         if(pageLayoutControl.checkMxFile(fileChosen)) {
            pageLayoutControl.setMousePointer(esriControlsMousePointer.
                                              esriPointerHourglass);
            pageLayoutControl.loadMxFile(fileChosen, null);
            pageLayoutControl.setMousePointer(esriControlsMousePointer.esriPointerDefault);
         }
         else
            JOptionPane.showMessageDialog(this, fileChosen + " is not a valid ArcMap Document");

         loaded = true;
        }
        else {
           loaded = false;
        }
     return loaded;

  }


   /**
    * Main program to start the program execution.
    *
    * @param s
    */


   public static void main(String s[]) {
      try {
         //Set the system look and feel
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         EngineInitializer.initializeVisualBeans();

        AoInitialize aoInit = new AoInitialize();
        aoInit.initialize(esriLicenseProductCode.esriLicenseProductCodeEngine);

         PrintPageLayout printPageLayout = new PrintPageLayout();
         printPageLayout.setDefaultCloseOperation(PrintPageLayout.EXIT_ON_CLOSE);
      }
      catch (Exception ex) {
         ex.printStackTrace();
      }
   }
}

