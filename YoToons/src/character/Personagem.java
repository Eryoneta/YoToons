package character;
import java.awt.Graphics2D;
import java.util.HashMap;

import org.w3c.dom.Element;
public class Personagem{
	private String nome;
		public String getNome(){return nome;}
		public void setNome(String nome){this.nome=nome;}
	private Esqueleto esqueleto;
		public Esqueleto getEsqueleto(){return esqueleto;}
		public void setEsqueleto(Esqueleto esqueleto){this.esqueleto=esqueleto;}
	private boolean show;		//SE INVISÍVEL OU NÃO
		public boolean getShow(){return show;}
		public void setShow(boolean show){this.show=show;}
		
	private HashMap<Integer,Forma>formas=new HashMap<>();
		public HashMap<Integer,Forma>getFormas(){return formas;}
	private HashMap<Integer,Linha>linhas=new HashMap<>();
		public HashMap<Integer,Forma>getLinhas(){return formas;}
		
	public Personagem(Element personagem){
		final HashMap<Integer,Ponto>pontos=new HashMap<>();
		setNome(personagem.getAttribute("nome"));													//INSERE NOME
		final Element tagEsqueleto=(Element)personagem.getElementsByTagName("esqueleto").item(0);	//INSERE ESQUELETO
		setEsqueleto(new Esqueleto(tagEsqueleto,formas,linhas,pontos));
	}
//	private double size=50;
//	private Ponto3D[]pontos3D=new Ponto3D[]{
//			new Ponto3D(-size, -size, -size),
//			new Ponto3D(size, -size, -size),
//			new Ponto3D(size, size, -size),
//			new Ponto3D(-size, size, -size),
//			new Ponto3D(-size, -size, size),
//			new Ponto3D(size, -size, size),
//			new Ponto3D(size, size, size),
//			new Ponto3D(-size, size, size)
//	};
//	private Ponto3D[]pontos3D=new Ponto3D[]{
//			new Ponto3D(0,0,0),
//			new Ponto3D(size,size,size)
//	};
//	private double angle=0;
	public void draw(Graphics2D imagemEdit){
//		getEsqueleto().update();
//		for(Forma forma:getFormas())forma.draw(imagemEdit,local);
		
		getEsqueleto().draw(imagemEdit);

//		Ponto ppp=pontos3D[0].getPonto2D(70);
//		ppp.setLocation(ppp.getX()+300,ppp.getY()+300);
//		ppp.draw(imagemEdit,null);
		
//		angle+=0.05;
//		for(Ponto3D p:pontos3D){
//			Ponto3D rot=p.rotateZ(p,angle);
////			rot=p.rotateY(rot,angle);
////			rot=p.rotateZ(rot,angle);
//			Ponto pp=rot.getPonto2D(70);
//			pp.setLocation(pp.getX()+300,pp.getY()+300);
//			pp.draw(imagemEdit,null);
//		}
		
	}
}