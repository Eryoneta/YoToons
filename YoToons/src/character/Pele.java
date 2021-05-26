package character;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
public class Pele{
	private Carne carne;
		public Carne getCarne(){return carne;}
	private Diretriz3D absoluteDiretriz=new Diretriz3D();
		public double getAbsoluteAngleZ(){return absoluteDiretriz.getAngleZ();}
		public double getAbsoluteAngleY(){return absoluteDiretriz.getAngleY();}
//		public double getAbsoluteAngleX(){return absoluteDiretriz.getAngleX();}
		public Diretriz3D getAbsoluteOrientation(){return absoluteDiretriz;}
		public void setAbsoluteOrientation(Diretriz3D absoluteDiretriz){this.absoluteDiretriz=absoluteDiretriz;}
		public void setAbsolutevOrientation(double angleZ,double angleY){absoluteDiretriz.setOrientation(angleZ,angleY,0);}
	private Diretriz3D relativeDiretriz=new Diretriz3D();
		public double getRelativeAngleZ(){return relativeDiretriz.getAngleZ();}
		public double getRelativeAngleY(){return relativeDiretriz.getAngleY();}
		public double getRelativeAngleX(){return relativeDiretriz.getAngleX();}
		public Diretriz3D getRelativeOrientation(){return relativeDiretriz;}
		public void setRelativeOrientation(Diretriz3D relativeDiretriz){this.relativeDiretriz=relativeDiretriz;}
		public void setRelativeOrientation(double angleZ,double angleY,double angleX){relativeDiretriz.setOrientation(angleZ,angleY,angleX);}
	private List<Forma>formas=new ArrayList<>();
		public boolean add(Forma forma){return formas.add(forma);}
		public boolean del(Forma forma){return formas.remove(forma);}
		public List<Forma>getFormas(){return formas;}
	private List<Linha>linhas=new ArrayList<>();
		public boolean add(Linha linha){return linhas.add(linha);}
		public boolean del(Linha linha){return linhas.remove(linha);}
		public List<Linha>getLinhas(){return linhas;}
	private List<Ponto>pontos=new ArrayList<>();
		public boolean add(Ponto ponto){return pontos.add(ponto);}
		public boolean del(Ponto ponto){return pontos.remove(ponto);}
		public List<Ponto>getPontos(){return pontos;}
	public Pele(Carne carne){this.carne=carne;}
	public Pele(Carne carne,Element pele,HashMap<Integer,Forma>formasIDs,HashMap<Integer,Linha>linhasIDs,HashMap<Integer,Ponto>pontosIDs){
		this.carne=carne;													//INSERE CARNE
		getAbsoluteOrientation().setAngleZ(Double.parseDouble(pele.getAttribute("absoluteAngleZ")));		//INSERE ABSOLUTE-ANGLE-Z
		getAbsoluteOrientation().setAngleY(Double.parseDouble(pele.getAttribute("absoluteAngleY")));		//INSERE ABSOLUTE-ANGLE-Y
//		getAbsoluteOrientation().setAngleX(Double.parseDouble(pele.getAttribute("absoluteAngleX")));		//INSERE ABSOLUTE-ANGLE-X
		getRelativeOrientation().setAngleZ(Double.parseDouble(pele.getAttribute("relativeAngleZ")));		//INSERE RELATIVE-ANGLE-Z
		getRelativeOrientation().setAngleY(Double.parseDouble(pele.getAttribute("relativeAngleY")));		//INSERE RELATIVE-ANGLE-Y
		getRelativeOrientation().setAngleX(Double.parseDouble(pele.getAttribute("relativeAngleX")));		//INSERE RELATIVE-ANGLE-X
		final NodeList linhas=pele.getElementsByTagName("linha");			//INSERE LINHAS
		for(int i=0,size=linhas.getLength();i<size;i++){
			final Element tagLinha=(Element)linhas.item(i);
			add(new Linha(tagLinha,linhasIDs,pontosIDs));
		}
		final NodeList formas=pele.getElementsByTagName("forma");			//INSERE FORMAS
		for(int i=0,size=formas.getLength();i<size;i++){
			final Element tagForma=(Element)formas.item(i);
			add(new Forma(tagForma,formasIDs,linhasIDs,pontosIDs));
		}
		final NodeList pontos=pele.getElementsByTagName("ponto");			//INSERE PONTOS
		for(int i=0,size=pontos.getLength();i<size;i++){
			final Element tagPonto=(Element)pontos.item(i);
			add(new Ponto(tagPonto,pontosIDs));
		}
	}
	public static Pele getInBetweenRelativeX(Pele pele1,double angleX,Pele pele2){
		final Pele pele=getInBetween(pele1.getRelativeAngleX(),pele1,angleX,pele2,pele2.getRelativeAngleX());
		pele.setAbsoluteOrientation(pele1.getAbsoluteOrientation());
		pele.setRelativeOrientation(pele1.getRelativeAngleZ(),pele1.getRelativeAngleY(),angleX);
		return pele;
	}
	public static Pele getInBetweenRelativeY(Pele pele1,double angleY,Pele pele2){
		final Pele pele=getInBetween(pele1.getRelativeAngleY(),pele1,angleY,pele2,pele2.getRelativeAngleY());
		pele.setAbsoluteOrientation(pele1.getAbsoluteOrientation());
		pele.setRelativeOrientation(pele1.getRelativeAngleZ(),angleY,pele1.getRelativeAngleX());
		return pele;
	}
	public static Pele getInBetweenRelativeZ(Pele pele1,double angleZ,Pele pele2){
		final Pele pele=getInBetween(pele1.getRelativeAngleZ(),pele1,angleZ,pele2,pele2.getRelativeAngleZ());
		pele.setAbsoluteOrientation(pele1.getAbsoluteOrientation());
		pele.setRelativeOrientation(angleZ,pele1.getRelativeAngleY(),pele1.getRelativeAngleX());
		return pele;
	}
	public static Pele getInBetweenAbsoluteY(Pele pele1,double angleY,Pele pele2){
//		System.out.println("|");
//		System.out.println(pele1.getAbsoluteAngleY()+" | "+pele2.getAbsoluteAngleY());
		final Pele pele=getInBetween(pele1.getAbsoluteAngleY(),pele1,angleY,pele2,pele2.getAbsoluteAngleY());
//		System.out.println("|");
		pele.setAbsolutevOrientation(pele1.getAbsoluteAngleZ(),angleY);
		pele.setRelativeOrientation(pele1.getRelativeOrientation());
		return pele;
	}
	public static Pele getInBetweenAbsoluteZ(Pele pele1,double angleZ,Pele pele2){
		final Pele pele=getInBetween(pele1.getAbsoluteAngleZ(),pele1,angleZ,pele2,pele2.getAbsoluteAngleZ());
		pele.setAbsolutevOrientation(angleZ,pele1.getAbsoluteAngleY());
		pele.setRelativeOrientation(pele1.getRelativeOrientation());
		return pele;
	}
	private static Pele getInBetween(double angleAnt,Pele pele1,double angle,Pele pele2,double anglePos){
		final Pele peleInBetween=new Pele(pele1.getCarne());
		for(Ponto ponto:pele1.getPontos())peleInBetween.add(new Ponto(ponto));
		for(Linha linha:pele1.getLinhas())peleInBetween.add(new Linha(linha));
		for(Forma formas:pele1.getFormas())peleInBetween.add(new Forma(formas));
		for(Ponto pontoAnt:peleInBetween.getPontos()){
			for(Ponto pontoPos:pele2.getPontos())if(pontoAnt.getPontoFinal()==pontoPos.getPontoFinal()){
				final double x=getInBetween(angleAnt,pontoAnt.getX(),angle,pontoPos.getX(),anglePos);
				final double y=getInBetween(angleAnt,pontoAnt.getY(),angle,pontoPos.getY(),anglePos);

//				System.out.println(pontoAnt.getY()+" | "+pontoPos.getY()+" = "+y);
//				System.out.println(angleAnt+" | "+anglePos);
				
				pontoAnt.setLocation(x,y);
				final double size=getInBetween(angleAnt,pontoAnt.getSize(),angle,pontoPos.getSize(),anglePos);
				pontoAnt.setSize(size);
				break;
			}
		}
		return peleInBetween;
	}
	private static double getInBetween(double angleAnt,double localAnt,double angle,double localPos,double anglePos){
		if(localAnt==localPos)return localAnt;	//FALHO POR SER DOUBLE
		localAnt=Math.min(localAnt,localPos);
		localPos=Math.max(localAnt,localPos);
		angleAnt=Math.min(angleAnt,anglePos);
		anglePos=Math.max(angleAnt,anglePos);
		final double local=(double)((((double)(localPos-localAnt)*(double)(angle-angleAnt))/(double)(anglePos-angleAnt))+localAnt);
		return local;
	}
	public void draw(Graphics2D imagemEdit){
		for(Forma forma:getFormas())forma.draw(imagemEdit,getCarne().getOsso().getAbsoluteLocation().getPonto2D(),getCarne().getOsso().getAbsoluteAngleX());
		for(Linha linha:getLinhas())linha.draw(imagemEdit,getCarne().getOsso().getAbsoluteLocation().getPonto2D(),getCarne().getOsso().getAbsoluteAngleX());
	}
}