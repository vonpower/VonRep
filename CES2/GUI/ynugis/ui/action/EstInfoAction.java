package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class EstInfoAction extends Action {
	public final String info_business="房屋出租租金资料,土地使用权出让价格,土地使用权转让价格,土地使用权出租租金,柜台出租租金,商业用地效益,产业用地效益,服务业用地效益,文体设施用地效益";
	public final String info_dwell="房屋买卖价格资料，商品房出售价格资料";
	public final String info_industry="土地联营入股资料，联合建房资料，以房换房资料，征地、拆迁开发土地资料";
	public final String[] info=new String[]{info_business,info_dwell,info_industry};
	private final String info_default = "可使用资料:";
	private MainFace face;

	public EstInfoAction(MainFace m) {
		this.face = m;
		this.setText("说明");
		ImageRegistry imageRegistry = face.getCesui().getImageRegistry();
		if (imageRegistry != null) {
			this.setImageDescriptor(imageRegistry
					.getDescriptor(ImageProvider.ICON_ESTINFO));
		}
	}

	public void setText(String text) {
		String newText=this.info_default+text;
		super.setText(newText);
	}

	public void run() {
		String msg=this.info_default+"\n\n         收益还原法:"+this.info_business+"\n\n         剩余法:"+this.info_dwell+"\n\n         成本逼近法:"+this.info_industry;
		MessageDialog.openInformation(face.getShell(),"说明",msg);
	}
	
}
