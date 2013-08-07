/*
 * @author 冯涛，创建日期：2003-11-25
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.estimate.wizard;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import ynugis.classifyBusiness.ClassifyProject;
import ynugis.estimate.core.EstimateRule;
import ynugis.estimate.data.PublicProperty;
import ynugis.estimate.data.Revise;
import ynugis.estimate.page.CalculatePage;
import ynugis.estimate.page.DataSpecifyPage;
import ynugis.estimate.page.EliminatePage;
import ynugis.estimate.page.PublicPropertyPage;
import ynugis.estimate.page.RevisePage;
import ynugis.estimate.page.SamplePointPage;
import ynugis.utility.GT;
import ynugis.utility.GV;

import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.interop.AutomationException;

public class EstimateWizard extends Wizard implements IRunnableWithProgress {

	private DataSpecifyPage dataSpecifyPage;

	private PublicPropertyPage publicPropertyPage;

	private RevisePage revisePage;

	private SamplePointPage samplePointPage;

	private EliminatePage eliminatePage;

	private CalculatePage calculatePage;

	private ClassifyProject project;

	static boolean flag = true;

	public EstimateWizard(ClassifyProject inProject) {
		project = inProject;
		
	}

	public boolean performFinish() {
		ProgressMonitorDialog pmd = new ProgressMonitorDialog(getShell());

		try {
			pmd.run(false, false, this);

		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void addPages() {
		int count = 0;
		int total = 6;
		dataSpecifyPage = new DataSpecifyPage(project);

		dataSpecifyPage.setTitle("土地估价向导 " + count + "/" + total);
		dataSpecifyPage
				.setDescription("指定用于本次估价的单元格分值图（CellValueMap） ，指定分级、分段数据");

		publicPropertyPage = new PublicPropertyPage();
		publicPropertyPage.setTitle("土地估价向导 " + count + "/" + total);
		publicPropertyPage.setDescription("请核查下面的公共属性表：");

		samplePointPage = new SamplePointPage();
		samplePointPage.setTitle("土地估价向导 " + count + "/" + total);
		samplePointPage.setDescription("请指定“房屋租赁样本点”数据，并设定样本点的检验和剔除的相关规则");

		revisePage = new RevisePage();
		revisePage.setTitle("土地估价向导 " + count + "/" + total);
		revisePage.setDescription("请在下拉框中选择要填写的修正项目并填写");

		eliminatePage = new EliminatePage();
		eliminatePage.setTitle("土地估价向导 " + count + "/" + total);
		eliminatePage.setDescription("配置剔除规则");

		calculatePage = new CalculatePage();
		calculatePage.setTitle("土地估价向导 " + count + "/" + total);
		calculatePage.setDescription("在这一页中系统将按照前面您输入的信息进行收益还原法地价测算。");

		addPage(dataSpecifyPage);
		addPage(publicPropertyPage);
		addPage(samplePointPage);
		addPage(revisePage);
		addPage(eliminatePage);

		addPage(calculatePage);

		super.addPages();
	}

	public boolean canFinish() {
		// 达到最后一页才能点Finish按钮
		if (this.getContainer().getCurrentPage() != calculatePage)
			return false;

		return super.canFinish();
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {

		monitor.beginTask("开始进行地价评估！", 10);

		final IFeatureClass cellValue= dataSpecifyPage.getCellValueMap().getAsPloygonFeatureClass();
		
		// final IRaster
		// cellValueRaster=dataSpecifyPage.getCellValueMap().getAsRaster();

		final IFeatureClass simplePoint = samplePointPage
				.getSimplePointFeatureClass();
		final String dataName=samplePointPage.getDataName();
		final Revise classifyRevise = dataSpecifyPage.getClassifyRevise();
		classifyRevise.multiple(GV.multiple);

		final PublicProperty pp = publicPropertyPage.getPublicProperty();
		final Revise revises[] = revisePage.getRevises();
		
		final boolean avgFlag = calculatePage.getAvgButton().getSelection();
		final boolean expFlag = calculatePage.getExpButton().getSelection();
		final boolean saveFlag = calculatePage.getIsSaveButton().getSelection();
		final String savePath = calculatePage.getResultText().getText();
		
		final int SDMultiple = eliminatePage.getSDNum();
		final String[] checkFields=eliminatePage.getCheckFields();
		new Thread(new Runnable() {

			public void run() {
				try {
					
					EstimateRule er=new EstimateRule(simplePoint,cellValue,classifyRevise);
					
					//检验、剔除
					er.eliminate(checkFields,SDMultiple);
					//修正
					er.revise(revises);
					//计算V值
					er.calculateV(dataName);
															
					String temp = "";
					if (expFlag) {
						temp+=er.estimate_ExpModel();
					}
					if (avgFlag) {
						temp += er.estimate_AvgModel();
					}

					if (saveFlag) {
						GT.write2Txt(savePath, temp);
					}

					EstimateWizard.flag = false;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

		while (flag) {

			Thread.sleep(20000);
			monitor.worked(1);
		}
		flag = true;
		monitor.done();

	}
	public IWizardPage getNextPage(IWizardPage page) {

		IWizardPage retPage = super.getNextPage(page);
		if (page.getName() == "SamplePointPage") {
			RevisePage rp = (RevisePage) retPage;
			try {
				IFeatureClass temp = samplePointPage
						.getSimplePointFeatureClass();
				if (temp != null) {
					rp.setFields(temp.getFields());
				}
			} catch (AutomationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (page.getName() == "RevisePage") {
			EliminatePage ep = (EliminatePage) retPage;
			try {
				ep.setFields(samplePointPage.getSimplePointFeatureClass()
						.getFields());
			} catch (AutomationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return retPage;
	}

}
