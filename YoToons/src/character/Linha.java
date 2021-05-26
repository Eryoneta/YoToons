package character;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
public class Linha{
	private Linha linhaFinal;
		public Linha getLinhaFinal(){return linhaFinal;}
		public void setLinhaFinal(Linha linhaFinal){this.linhaFinal=linhaFinal;}
	private List<Ponto>pontos=new ArrayList<>();
		public boolean add(Ponto ponto){return pontos.add(ponto);}
		public boolean del(Ponto ponto){return pontos.remove(ponto);}
		public Ponto get(int index){return (index<0?pontos.get(0):index>=pontos.size()?pontos.get(pontos.size()-1):pontos.get(index));}
		public List<Ponto>getPontos(){return pontos;}
	private Color cor=Color.BLACK;
		public Color getCor(){return cor;}
		public void setCor(Color cor){this.cor=cor;}
	private boolean show;
		public boolean getShow(){return show;}
		public void setShow(boolean show){this.show=show;}
	public Linha(){}
	public Linha(Linha linha){
		for(Ponto ponto:linha.getPontos())add(new Ponto(ponto));
	}
	public Linha(Element linha,HashMap<Integer,Linha>listaIDs,HashMap<Integer,Ponto>pontosIDs){
		final int id=Integer.parseInt(linha.getAttribute("id"));
		if(!listaIDs.containsKey(id))listaIDs.put(id,new Linha());
		linhaFinal=listaIDs.get(id);
		final NodeList pontos=linha.getElementsByTagName("ponto");
		for(int i=0,size=pontos.getLength();i<size;i++){
			final Element tagPonto=(Element)pontos.item(i);
			add(new Ponto(tagPonto,pontosIDs));
		}
	}
	private Ponto catmullRoom(double t,Ponto p0,Ponto p1,Ponto p2,Ponto p3){	//MAGIA!
		//q(t)=0.5*((2*P1)+(((-P0)+P2)*t)+(((2*P0)-(5*P1)+(4*P2)-P3)*(t^2))+(((-P0)+(3*P1)-(3*P2)+P3)*(t^3)))
		return (p1.scale(2).add(p0.scale(-1).add(p2).scale(t)).add(p0.scale(2).sub(p1.scale(5)).add(p2.scale(4)).sub(p3).scale(t*t))
				.add(p0.scale(-1).add(p1.scale(3)).sub(p2.scale(3)).add(p3).scale(t*t*t)).scale(0.5));
	}
	private List<Ponto>getAllPontos(){
		final List<Ponto>pontos=new ArrayList<Ponto>();
		for(int p=0,size=getPontos().size();p<size;p++){
			final double widthIni=get(p+0).getSize(),widthFim=get(p+1).getSize();
			final double variancia=(double)(widthFim-widthIni);
			final double dist=get(p+0).getDistance(get(p+1));
			final double passos=1/(p==size-1?dist:Math.max(dist,get(p+0).getSize()+get(p+1).getSize()));			//ENTRE 0 E 1, A "DISTÂNCIA" ENTRE OS PONTOS
			for(double subP=0;subP<=1;subP+=passos){
				final Ponto subPonto=new Ponto(catmullRoom(subP,get(p-1),get(p+0),get(p+1),get(p+2)));				//MÁGICA DO CATMULLROOM
				subPonto.setSize((double)(widthIni+(subP/1)*variancia));
				pontos.add(subPonto);
			}
		}
		return pontos;
	}
	public GeneralPath getForm(Ponto local,double angleX){
		final List<Ponto>pontos=getAllPontos();
		for(Ponto ponto:pontos)ponto.set(ponto.getRelativeLocal(local,angleX));
		final GeneralPath forma=new GeneralPath();
		forma.moveTo(pontos.get(0).getX(),pontos.get(0).getY());
		for(int p=0,size=pontos.size();p<size;p++){									//VAI
			final Ponto pontoAnt=pontos.get(p-(p==0?-1:1));							//CASO SEJA O 1º PONTO, SE ORIENTA COM O 2º PONTO
			final Ponto pontoNow=pontos.get(p);
			final double angleAnt=pontoNow.getAngle(pontoAnt);
			final Ponto ponto=pontoNow.getRelativePoint(angleAnt-(p==0?-90:90),pontoNow.getSize()/2);
			if(p==0){
				final double anglePasso=90/pontoNow.getSize(); 
				for(double c=pontoNow.getSize();c>0;c--){
					final Ponto pontoCurva=pontoNow.getRelativePoint(angleAnt-(anglePasso*c),pontoNow.getSize()/2);
					forma.lineTo(pontoCurva.getX(),pontoCurva.getY());
				}
			}
			forma.lineTo(ponto.getX(),ponto.getY());
			if(p==size-1){
				final double anglePasso=90/pontoNow.getSize(); 
				for(double c=pontoNow.getSize();c>0;c--){
					final Ponto pontoCurva=pontoNow.getRelativePoint(angleAnt-(anglePasso*c),pontoNow.getSize()/2);
					forma.lineTo(pontoCurva.getX(),pontoCurva.getY());
				}
			}
		}
		for(int size=pontos.size(),p=size-1;p>=0;p--){								//VOLTA
			final Ponto pontoAnt=pontos.get(p-(p==0?-1:1));							//CASO SEJA O 1º PONTO, SE ORIENTA COM O 2º PONTO
			final Ponto pontoNow=pontos.get(p);
			final double angleAnt=pontoNow.getAngle(pontoAnt);
			final Ponto ponto=pontoNow.getRelativePoint(angleAnt+(p==0?-90:90),pontoNow.getSize()/2);
			if(p==size-1){
				final double anglePasso=90/pontoNow.getSize(); 
				for(double c=0;c<pontoNow.getSize();c++){
					final Ponto pontoCurva=pontoNow.getRelativePoint(angleAnt+(anglePasso*c),pontoNow.getSize()/2);
					forma.lineTo(pontoCurva.getX(),pontoCurva.getY());
				}
			}
			forma.lineTo(ponto.getX(),ponto.getY());
			if(p==0){
				final double anglePasso=90/pontoNow.getSize(); 
				for(double c=0;c<pontoNow.getSize();c++){
					final Ponto pontoCurva=pontoNow.getRelativePoint(angleAnt+(anglePasso*c),pontoNow.getSize()/2);
					forma.lineTo(pontoCurva.getX(),pontoCurva.getY());
				}
			}
		}
		forma.closePath();
		return forma;
	}
	public Path2D.Double getLine(Ponto local,double angleX){
		final List<Ponto>pontos=getAllPontos();
		for(Ponto ponto:pontos)ponto.set(ponto.getRelativeLocal(local,angleX));
		final Path2D.Double linha=new Path2D.Double();
		linha.moveTo(pontos.get(0).getX(),pontos.get(0).getY());
		for(Ponto ponto:pontos)linha.lineTo(ponto.getX(),ponto.getY());
		
//		for(int p=0,size=getPontos().size();p<size;p++){
//			final double dist=get(p+0).getDistance(get(p+1));
//			final double passos=1/(p==size-1?dist:Math.max(dist,(double)(get(p+0).getSize()+get(p+1).getSize())));
//			final Ponto p1=get(p-1).getRelativeLocal(local,angleX);
//			final Ponto c1=get(p+0).getRelativeLocal(local,angleX);
//			final Ponto c2=get(p+1).getRelativeLocal(local,angleX);
//			final Ponto p2=get(p+2).getRelativeLocal(local,angleX);
//			for(double subP=0;subP<=1;subP+=passos){
//				final Ponto subPonto=catmullRoom(subP,p1,c1,c2,p2);
//				linha.lineTo(subPonto.getX(),subPonto.getY());
//			}
//		}
		return linha;
	}
//	public GeneralPath getRelativeForm(Ponto local,double angleX){
//		final GeneralPath forma=getForm(local,angleX);
//		forma.transform(AffineTransform.getRotateInstance(angleX));
//		forma.transform(AffineTransform.getTranslateInstance(local.getX(),local.getY()));
//		return forma;
//	}
//	public Path2D.Double getRelativeLine(Ponto local,double angleX){
//		final Path2D.Double linha=getLine();
//		linha.transform(AffineTransform.getRotateInstance(angleX));
//		linha.transform(AffineTransform.getTranslateInstance(local.getX(),local.getY()));
//		return linha;
//	}
	public void draw(Graphics2D imagemEdit,Ponto local,double angleX){
		imagemEdit.setColor(getCor());
		imagemEdit.fill(getForm(local,angleX));
		imagemEdit.setColor(Color.BLUE);
		imagemEdit.draw(getLine(local,angleX));
		for(Ponto p:getPontos())p.draw(imagemEdit,local,angleX);
	}
}