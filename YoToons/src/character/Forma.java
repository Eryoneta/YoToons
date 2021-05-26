package character;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
public class Forma{
	private Forma formaFinal;
		public Forma getFormaFinal(){return formaFinal;}
		public void setFormaFinal(Forma formaFinal){this.formaFinal=formaFinal;}
	private List<Linha>linhas=new ArrayList<>();
		public boolean add(Linha linha){return linhas.add(linha);}
		public boolean del(Linha linha){return linhas.remove(linha);}
		public List<Linha>getLinhas(){return linhas;}
	private Color cor=Color.GREEN;
		public Color getCor(){return cor;}
		public void setCor(Color cor){this.cor=cor;}
	private boolean show;
		public boolean getShow(){return show;}
		public void setShow(boolean show){this.show=show;}
	public Forma(){}
	public Forma(Forma forma){
		for(Linha linha:forma.getLinhas())add(new Linha(linha));
	}
	public Forma(Element forma,HashMap<Integer,Forma>formasIDs,HashMap<Integer,Linha>linhasIDs,HashMap<Integer,Ponto>pontosIDs){
		final int id=Integer.parseInt(forma.getAttribute("id"));
		if(!formasIDs.containsKey(id))formasIDs.put(id,new Forma());
		formaFinal=formasIDs.get(id);
		final NodeList linhas=forma.getElementsByTagName("linha");
		for(int i=0,size=linhas.getLength();i<size;i++){
			final Element tagLinha=(Element)linhas.item(i);
			add(new Linha(tagLinha,linhasIDs,pontosIDs));
		}
	}
	public Path2D.Double getForm(Ponto local,double angleX){
		final Path2D.Double forma=new Path2D.Double();
		for(Linha linha:getLinhas())forma.append(linha.getLine(local,angleX),true);
		return forma;
	}
//	public Path2D.Double getRelativeForm(Ponto local,double angleX){
//		final Path2D.Double forma=getForm();
//		forma.transform(AffineTransform.getRotateInstance(angleX));
//		forma.transform(AffineTransform.getTranslateInstance(local.getX(),local.getY()));
//		return forma;
//	}
	public void draw(Graphics2D imagemEdit,Ponto local,double angleX){
		imagemEdit.setColor(getCor());
		imagemEdit.fill(getForm(local,angleX));
	}
}