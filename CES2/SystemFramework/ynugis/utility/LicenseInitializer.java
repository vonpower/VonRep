/*
 * LicenseInitializer.java
 *
 * Created on October 7, 2004, 8:59 AM
 */

package ynugis.utility;



import java.io.IOException;

import javax.swing.JOptionPane;

import com.esri.arcgis.system.AoInitialize;
import com.esri.arcgis.system.IAoInitialize;
import com.esri.arcgis.system.esriLicenseExtensionCode;
import com.esri.arcgis.system.esriLicenseStatus;



/**
 *
 * @author  KevinD
 */
public class LicenseInitializer {
    
    public  IAoInitialize m_AoInitialize;
    private int licenseStatus;
    private int Product;
    private int Extension; 
    
    /** Creates a new instance of LicenseInitializer */
    public LicenseInitializer(int productCode)throws IOException {
        
        m_AoInitialize = new AoInitialize();
        Product = productCode;
        Extension = 0;
    }
  
    public LicenseInitializer(int productCode,int esriLicenseExtensionCode)throws IOException {
        
        m_AoInitialize = new AoInitialize();
        Product = productCode;
        Extension = esriLicenseExtensionCode;
        
    }                
 
    
 //Initialize the application with a license
  public  boolean InitializeApplication()throws IOException{
        
        boolean bInitialized = true;
        if (m_AoInitialize == null) {
            JOptionPane.showMessageDialog(null,
            "Unable to initialize ArcGIS. This application cannot run! Please check that ArcGIS (Desktop, Engine or Server) is installed.",
                                         "ESRI License Initializer",
                                         JOptionPane.WARNING_MESSAGE);
          
            bInitialized = false;
        }
        //Initialize the application
        int licenseStatus = esriLicenseStatus.esriLicenseUnavailable;
        licenseStatus = CheckOutLicenses(Product);
        if (licenseStatus != esriLicenseStatus.esriLicenseCheckedOut) {
            JOptionPane.showMessageDialog(null,
            LicenseMessage(licenseStatus),
            "ESRI License Initializer",
            JOptionPane.WARNING_MESSAGE);
            bInitialized = false;
        }
        
        return bInitialized;
    }
    
    private String LicenseMessage(int licenseStatus) {
        String message = "";
        
        //Not licensed
        if (licenseStatus == esriLicenseStatus.esriLicenseNotLicensed) {
            message = "You are not licensed to run this product!";
        }
        //The licenses needed are currently in use
        else if (licenseStatus == esriLicenseStatus.esriLicenseUnavailable) {
            message = "There are insuffient licenses to run!";
        }
        //The licenses unexpected license failure
        else if (licenseStatus == esriLicenseStatus.esriLicenseFailure) {
            message = "Unexpected license failure! Please contact your administrator.";
        }
        //Already initialized (Initialization can only occur once)
        else if (licenseStatus == esriLicenseStatus.esriLicenseAlreadyInitialized) {
            message = "The license has already been initialized! Please check your implementation.";
        }
        return message;
    }
    
    private int CheckOutLicenses(int productCode)throws IOException {
        int licenseStatus;
        //Determine if the product is available
        licenseStatus = m_AoInitialize.isProductCodeAvailable(productCode);
        if (licenseStatus == esriLicenseStatus.esriLicenseAvailable) {
          switch (Extension){
             case 0: {
             //Initialize the license
             licenseStatus = m_AoInitialize.initialize(productCode);
             }
             case 9: {
             //Determine if the extensions are available
             licenseStatus = m_AoInitialize.isExtensionCodeAvailable(productCode, esriLicenseExtensionCode.esriLicenseExtensionCode3DAnalyst);
             if (licenseStatus == esriLicenseStatus.esriLicenseAvailable) {
            //Initialize the license
             licenseStatus = m_AoInitialize.initialize(productCode);
             //Checkout the extensions
             if (licenseStatus == esriLicenseStatus.esriLicenseCheckedOut) {
             licenseStatus = m_AoInitialize.checkOutExtension(esriLicenseExtensionCode.esriLicenseExtensionCode3DAnalyst);
              }
             }
            }
          }
        }
       return  licenseStatus;
    }
   
    public void ShutdownApplication()throws IOException {
        if (m_AoInitialize == null) return;
        
        if (Extension == 9){
        //Checkin the extensions
        m_AoInitialize.checkInExtension(esriLicenseExtensionCode.esriLicenseExtensionCode3DAnalyst);
        }  
        //Shut down the AoInitilaize object
        m_AoInitialize.shutdown();
        m_AoInitialize = null;
    }
}
