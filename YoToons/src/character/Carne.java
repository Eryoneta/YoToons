package character;
import java.awt.Graphics2D;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
public class Carne{
	private Osso osso;
		public Osso getOsso(){return osso;}
	//		ABS-Z		Z	  ABS-Y	  Y		 REL-Z	  Z		REL-Y	Y	  REL-X	  X		PELE
	private HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>>>peles=new HashMap<>();
		public HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>>>getPeles(){return peles;}
		public HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>>getAbsoluteAngleZ(double absoluteAngleZ){			//ABSOLUTE-ANGLE-Z
			return getPeles().get(absoluteAngleZ);
		}
		public HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>getAbsoluteAngleY(double absoluteAngleZ,double absoluteAngleY){			//ABSOLUTE-ANGLE-Y
			return getAbsoluteAngleZ(absoluteAngleZ).get(absoluteAngleY);
		}
		public HashMap<Double,HashMap<Double,Pele>>getRelativeAngleZ(double absoluteAngleZ,double absoluteAngleY,double relativeAngleZ){		//RELATIVE-ANGLE-Z
			return getAbsoluteAngleY(absoluteAngleZ,absoluteAngleY).get(relativeAngleZ);
		}
		public HashMap<Double,Pele>getRelativeAngleY(double absoluteAngleZ,double absoluteAngleY,double relativeAngleZ,double relativeAngleY){		//RELATIVE-ANGLE-Y
			return getRelativeAngleZ(absoluteAngleZ,absoluteAngleY,relativeAngleZ).get(relativeAngleY);
		}
		public Pele getRelativeAngleX(double absoluteAngleZ,double absoluteAngleY,double relativeAngleZ,double relativeAngleY,double relativeAngleX){	//RELATIVE-ANGLE-X(PELE)
			return getRelativeAngleY(absoluteAngleZ,absoluteAngleY,relativeAngleZ,relativeAngleY).get(relativeAngleX);
		}
		
		public HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>getAbsoluteAngleY(Diretriz3D absoluteRotation){			//ABSOLUTE-ANGLE-Y
			return getAbsoluteAngleZ(absoluteRotation.getAngleZ()).get(absoluteRotation.getAngleY());
		}
		public HashMap<Double,HashMap<Double,Pele>>getRelativeAngleZ(Diretriz3D absoluteRotation,double relativeAngleZ){		//RELATIVE-ANGLE-Z
			return getAbsoluteAngleY(absoluteRotation).get(relativeAngleZ);
		}
		public HashMap<Double,Pele>getRelativeAngleY(Diretriz3D absoluteRotation,double relativeAngleZ,double relativeAngleY){		//RELATIVE-ANGLE-Y
			return getRelativeAngleZ(absoluteRotation,relativeAngleZ).get(relativeAngleY);
		}
		public Pele getRelativeAngleX(Diretriz3D absoluteRotation,Diretriz3D relativeRotation){
			return getRelativeAngleY(absoluteRotation,relativeRotation.getAngleZ(),relativeRotation.getAngleY()).get(relativeRotation.getAngleX());
		}
//		public void setPeles(HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>>>>peles){this.peles=peles;}	//NÃO USADO
//	private Pele peleBase;
//		public Pele getPeleBase(){return peleBase;}
		
		
//	//		PAI-Z	Z	  PAI-Y	  Y		PAI-X	X	  SON-Z	  Z		SON-Y	Y	  SON-X	  X		PELE
//	private HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>>>>peles=new HashMap<>();
//		public HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>>>>getPeles(){return peles;}
//		public HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>>>getAnglePaiZ(double anglePaiZ){			//ANGLE-PAI-Z
//			return getPeles().get(anglePaiZ);
//		}
//		public HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>>getAnglePaiY(double anglePaiZ,double anglePaiY){			//ANGLE-PAI-Y
//			return getAnglePaiZ(anglePaiZ).get(anglePaiY);
//		}
//		public HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>getAnglePaiX(double anglePaiZ,double anglePaiY,double anglePaiX){		//ANGLE-PAI-X
//			return getAnglePaiY(anglePaiZ,anglePaiY).get(anglePaiX);
//		}
//		public HashMap<Double,HashMap<Double,Pele>>getAngleZ(double anglePaiZ,double anglePaiY,double anglePaiX,double angleZ){				//ANGLE-Z
//			return getAnglePaiX(anglePaiZ,anglePaiY,anglePaiX).get(angleZ);
//		}
//		public HashMap<Double,Pele>getAngleY(double anglePaiZ,double anglePaiY,double anglePaiX,double angleZ,double angleY){				//ANGLE-Y
//			return getAngleZ(anglePaiZ,anglePaiY,anglePaiX,angleZ).get(angleY);
//		}
//		public Pele getAngleX(double anglePaiZ,double anglePaiY,double anglePaiX,double angleZ,double angleY,double angleX){				//ANGLE-X(PELE)
//			return getAngleY(anglePaiZ,anglePaiY,anglePaiX,angleZ,angleY).get(angleX);
//		}
//		public void setPeles(HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>>>>peles){this.peles=peles;}	//NÃO USADO
		public boolean add(Pele pele){
//			if(peleBase==null)peleBase=pele;
			
			final HashMap<Double,HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>>newAbsoluteAngleY=new HashMap<>();	//NOVA LISTA DE ABS-Y
			final HashMap<Double,HashMap<Double,HashMap<Double,Pele>>>newRelativeAngleZ=new HashMap<>();					//NOVA LISTA DE REL-Z
			final HashMap<Double,HashMap<Double,Pele>>newRelativeAngleY=new HashMap<>();									//NOVA LISTA DE REL-Y
			final HashMap<Double,Pele>newRelativeAngleX=new HashMap<>();													//NOVA LISTA DE REL-X
			
			final double absoluteAngleZ=pele.getAbsoluteAngleZ();
			final double absoluteAngleY=pele.getAbsoluteAngleY();
			final double relativeAngleZ=pele.getRelativeAngleZ();
			final double relativeAngleY=pele.getRelativeAngleY();
			final double relativeAngleX=pele.getRelativeAngleX();
			
//			System.out.println(absoluteAngleZ);
//			System.out.println(absoluteAngleY);
//			System.out.println(relativeAngleZ);
//			System.out.println(relativeAngleY);
//			System.out.println(relativeAngleX);
//			System.out.println("|");
			
			newRelativeAngleX.put(relativeAngleX,pele);
			newRelativeAngleY.put(relativeAngleY,newRelativeAngleX);
			newRelativeAngleZ.put(relativeAngleZ,newRelativeAngleY);
			newAbsoluteAngleY.put(absoluteAngleY,newRelativeAngleZ);
			
			if(getPeles().containsKey(absoluteAngleZ)){																			//EXISTE ABS-ANGLE-Z
				if(getAbsoluteAngleZ(absoluteAngleZ).containsKey(absoluteAngleY)){													//EXISTE ABS-ANGLE-Y
					if(getAbsoluteAngleY(absoluteAngleZ,absoluteAngleY).containsKey(relativeAngleZ)){									//EXISTE REL-ANGLE-Z
						if(getRelativeAngleZ(absoluteAngleZ,absoluteAngleY,relativeAngleZ).containsKey(relativeAngleY)){					//EXISTE REL-ANGLE-Y
							if(getRelativeAngleY(absoluteAngleZ,absoluteAngleY,relativeAngleZ,relativeAngleY).containsKey(relativeAngleX)){		//EXISTE REL-ANGLE-X
								getRelativeAngleY(absoluteAngleZ,absoluteAngleY,relativeAngleZ,relativeAngleY).put(relativeAngleX,pele);			//SUBSTITUI PELE
								return false;
							}else getRelativeAngleY(absoluteAngleZ,absoluteAngleY,relativeAngleZ,relativeAngleY).put(relativeAngleX,pele);		//SENÃO CRIA REL-ANGLE-Y 
						}else getRelativeAngleZ(absoluteAngleZ,absoluteAngleY,relativeAngleZ).put(relativeAngleY,newRelativeAngleX);		//SENÃO CRIA REL-ANGLE-Z
					}else getAbsoluteAngleY(absoluteAngleZ,absoluteAngleY).put(relativeAngleZ,newRelativeAngleY);						//SENÃO CRIA ABS-ANGLE-X
				}else getAbsoluteAngleZ(absoluteAngleZ).put(absoluteAngleY,newRelativeAngleZ);										//SENÃO CRIA ABS-ANGLE-Y
			}else getPeles().put(absoluteAngleZ,newAbsoluteAngleY);																//SENÃO CRIA ABS-ANGLE-Z

			return true;
		}
//		public Pele del(Pele pele){							//NÃO USADO
//			final float anglePaiR=pele.getAnglePaiR(),anglePaiY=pele.getAnglePaiY(),anglePaiX=pele.getAnglePaiX();
//			final float angleR=pele.getAngleR(),angleY=pele.getAngleY(),angleX=pele.getAngleX();
//			if(getPeles().containsKey(anglePaiR)){												//EXISTE ANGLE-PAI-R
//				if(getAnglePaiR(anglePaiR).containsKey(anglePaiY)){									//EXISTE ANGLE-PAI-Y
//					if(getAnglePaiY(anglePaiR,anglePaiY).containsKey(anglePaiX)){						//EXISTE ANGLE-PAI-X
//						if(getAnglePaiX(anglePaiR,anglePaiY,anglePaiX).containsKey(angleR)){				//EXISTE ANGLE-R
//							if(getAngleR(anglePaiR,anglePaiY,anglePaiX,angleR).containsKey(angleY)){			//EXISTE ANGLE-Y
//								if(getAngleY(anglePaiR,anglePaiY,anglePaiX,angleR,angleY).containsKey(angleX)){		//EXISTE ANGLE-X(É A PELE)
//									return getAngleY(anglePaiR,anglePaiY,anglePaiX,angleR,angleY).remove(angleX);		//REMOVE PELE
//								}																					//CRIA ANGLE-X
//							}																					//CRIA ANGLE-Y
//						}																					//CRIA ANGLE-R
//					}																					//CRIA ANGLE-PAI-X
//				}																					//CRIA ANGLE-PAI-Y
//			}																					//CRIA ANGLE-PAI-R
//			return null;
//		}
	private boolean show;
		public boolean getShow(){return show;}
		public void setShow(boolean show){this.show=show;}
	public Carne(Osso osso){this.osso=osso;}
	public Carne(Osso osso,Element carne,HashMap<Integer,Forma>formasIDs,HashMap<Integer,Linha>linhasIDs,HashMap<Integer,Ponto>pontosIDs){
		this.osso=osso;												//INSERE OSSO
		final NodeList peles=carne.getElementsByTagName("pele");	//INSERE PELES
		for(int i=0,size=peles.getLength();i<size;i++){
			final Element tagPele=(Element)peles.item(i);
			add(new Pele(this,tagPele,formasIDs,linhasIDs,pontosIDs));
		}
	}
	public Pele getPeleRotated(){
		if(getPeles().isEmpty())return null;
		
		final double absoluteAngleZAtual=getOsso().getAbsoluteAngleZ();
		final double absoluteAngleYAtual=getOsso().getAbsoluteAngleY();
//		final double absoluteAngleXAtual=getOsso().getAbsoluteAngleX();
		final double relativeAngleZAtual=getOsso().getRelativeAngleZ();
		final double relativeAngleYAtual=getOsso().getRelativeAngleY();
		final double relativeAngleXAtual=getOsso().getRelativeAngleX();
		
		double absoluteAngleZAnt=0;
		double absoluteAngleZPos=0;
		for(double absoluteAngleZ:getPeles().keySet()){absoluteAngleZPos=absoluteAngleZAnt=absoluteAngleZ;break;}	//PEGA O PRIMEIRO ÂNGULO AZ
		for(double absoluteAngleZ:getPeles().keySet()){
			if(absoluteAngleZAtual<=absoluteAngleZ){
				absoluteAngleZPos=absoluteAngleZ;
				break;
			}
			absoluteAngleZAnt=absoluteAngleZ;
		}
		
		double absoluteAngleYAnt=0;
		double absoluteAngleYPos=0;
		for(double absoluteAngleY:getAbsoluteAngleZ(absoluteAngleZAnt).keySet()){absoluteAngleYPos=absoluteAngleYAnt=absoluteAngleY;break;}	//PEGA O PRIMEIRO ÂNGULO ZY
		for(double absoluteAngleY:getAbsoluteAngleZ(absoluteAngleZAnt).keySet()){
//			System.out.println(absoluteAngleZAnt+" | "+absoluteAngleY);
			if(absoluteAngleYAtual<=absoluteAngleY){
				absoluteAngleYPos=absoluteAngleY;
				break;
			}
			absoluteAngleYAnt=absoluteAngleY;
		}
		
		double relativeAngleZAnt=0;
		double relativeAngleZPos=0;
		for(double relativeAngleZ:getAbsoluteAngleY(absoluteAngleZAnt,absoluteAngleYAnt).keySet()){
			relativeAngleZPos=relativeAngleZAnt=relativeAngleZ;break;}	//PEGA O PRIMEIRO ÂNGULO RZ
		for(double relativeAngleZ:getAbsoluteAngleY(absoluteAngleZAnt,absoluteAngleYAnt).keySet()){
			if(relativeAngleZAtual<=relativeAngleZ){
				relativeAngleZPos=relativeAngleZ;
				break;
			}
			relativeAngleZAnt=relativeAngleZ;
		}
		
		double relativeAngleYAnt=0;
		double relativeAngleYPos=0;
		for(double relativeAngleY:getRelativeAngleZ(absoluteAngleZAnt,absoluteAngleYAnt,relativeAngleZAnt).keySet()){
			relativeAngleYPos=relativeAngleYAnt=relativeAngleY;break;}	//PEGA O PRIMEIRO ÂNGULO RY
		for(double relativeAngleY:getRelativeAngleZ(absoluteAngleZAnt,absoluteAngleYAnt,relativeAngleZAnt).keySet()){
			if(relativeAngleYAtual<=relativeAngleY){
				relativeAngleYPos=relativeAngleY;
				break;
			}
			relativeAngleYAnt=relativeAngleY;
		}
		
		double relativeAngleXAnt=0;
		double relativeAngleXPos=0;
		for(double relativeAngleX:getRelativeAngleY(absoluteAngleZAnt,absoluteAngleYAnt,relativeAngleZAnt,relativeAngleYAnt).keySet()){
			relativeAngleXPos=relativeAngleXAnt=relativeAngleX;break;}	//PEGA O PRIMEIRO ÂNGULO RX
		for(double relativeAngleX:getRelativeAngleY(absoluteAngleZAnt,absoluteAngleYAnt,relativeAngleZAnt,relativeAngleYAnt).keySet()){
			if(relativeAngleXAtual<=relativeAngleX){
				relativeAngleXPos=relativeAngleX;
				break;
			}
			relativeAngleXAnt=relativeAngleX;
		}
		
		/*
			SE COMPARA DUAS PELES EM AZ,
		 	SE COMPARA DUAS PELES EM AY, COMPARADAS ANTES EM AZ,
		 	SE COMPARA DUAS PELES EM RZ, COMPARADAS ANTES EM AY, COMPARADAS ANTES EM AZ,
		 	SE COMPARA DUAS PELES EM RY, COMPARADAS ANTES EM RZ, COMPARADAS ANTES EM AY, COMPARADAS ANTES EM AZ,
		 	SE COMPARA DUAS PELES EM RX, COMPARADAS ANTES EM RY, COMPARADAS ANTES EM RZ, COMPARADAS ANTES EM AY, COMPARADAS ANTES EM AZ,
		 	O RESULTADO É UMA PELE ROTACIONADA EM TODAS AS POSSÍVEIS ROTAÇÕES EM RELAÇÃO AO PAI.
		 	RX TEM 1 COMPARAÇÃO,
		 	RY TEM 2 COMPARAÇÕES,
		 	RZ TEM 4 COMPARAÇÕES,
		 	AY TEM 8 COMPARAÇÕES,
		 	AZ TEM 16 COMPARAÇÕES,
		 	RESULTANDO EM 31 COMPARAÇÕES TOTAIS PARA DEFINIR A PELE DE UM OSSO.
		*/
		return getInBetweenRelativeX(
				absoluteAngleZAnt,absoluteAngleZAtual,absoluteAngleZPos,
				absoluteAngleYAnt,absoluteAngleYAtual,absoluteAngleYPos,
				relativeAngleZAnt,relativeAngleZAtual,relativeAngleZPos,
				relativeAngleYAnt,relativeAngleYAtual,relativeAngleYPos,
				relativeAngleXAnt,relativeAngleXAtual,relativeAngleXPos);
	}
	private Pele getInBetweenRelativeX(		//COMPARA DUAS PELES EM REL-X
			double absoluteAngleZAnt,double absoluteAngleZ,double absoluteAngleZPos,
			double absoluteAngleYAnt,double absoluteAngleY,double absoluteAngleYPos,
			double relativeAngleZAnt,double relativeAngleZ,double relativeAngleZPos,
			double relativeAngleYAnt,double relativeAngleY,double relativeAngleYPos,
			double relativeAngleXAnt,double relativeAngleX,double relativeAngleXPos){
		return Pele.getInBetweenRelativeX(
				getInBetweenRelativeY(absoluteAngleZAnt,absoluteAngleZ,absoluteAngleZPos,
						absoluteAngleYAnt,absoluteAngleY,absoluteAngleYPos,
						relativeAngleZAnt,relativeAngleZ,relativeAngleZPos,
						relativeAngleYAnt,relativeAngleY,relativeAngleYPos,
						relativeAngleXAnt),
				relativeAngleX,
				getInBetweenRelativeY(absoluteAngleZAnt,absoluteAngleZ,absoluteAngleZPos,
						absoluteAngleYAnt,absoluteAngleY,absoluteAngleYPos,
						relativeAngleZAnt,relativeAngleZ,relativeAngleZPos,
						relativeAngleYAnt,relativeAngleY,relativeAngleYPos,
						relativeAngleXPos)
		);
		
	}
	private Pele getInBetweenRelativeY(		//COMPARA DUAS PELES EM REL-Y
			double absoluteAngleZAnt,double absoluteAngleZ,double absoluteAngleZPos,
			double absoluteAngleYAnt,double absoluteAngleY,double absoluteAngleYPos,
			double relativeAngleZAnt,double relativeAngleZ,double relativeAngleZPos,
			double relativeAngleYAnt,double relativeAngleY,double relativeAngleYPos,
			double relativeAngleX){
		return Pele.getInBetweenRelativeY(
				getInBetweenRelativeZ(absoluteAngleZAnt,absoluteAngleZ,absoluteAngleZPos,
						absoluteAngleYAnt,absoluteAngleY,absoluteAngleYPos,
						relativeAngleZAnt,relativeAngleZ, relativeAngleZPos,
						relativeAngleYAnt,
						relativeAngleX),
				relativeAngleY,
				getInBetweenRelativeZ(absoluteAngleZAnt,absoluteAngleZ,absoluteAngleZPos,
						absoluteAngleYAnt,absoluteAngleY,absoluteAngleYPos,
						relativeAngleZAnt,relativeAngleZ, relativeAngleZPos,
						relativeAngleYPos,
						relativeAngleX)
		);
	}
	private Pele getInBetweenRelativeZ(		//COMPARA DUAS PELES EM REL-Z
			double absoluteAngleZAnt,double absoluteAngleZ,double absoluteAngleZPos,
			double absoluteAngleYAnt,double absoluteAngleY,double absoluteAngleYPos,
			double relativeAngleZAnt,double relativeAngleZ,double relativeAngleZPos,
			double relativeAngleY,
			double relativeAngleX){
		return Pele.getInBetweenRelativeZ(
				getInBetweenAbsoluteY(absoluteAngleZAnt,absoluteAngleZ,absoluteAngleZPos,
						absoluteAngleYAnt,absoluteAngleY,absoluteAngleYPos,
						relativeAngleZAnt,
						relativeAngleY,
						relativeAngleX),
				relativeAngleZ,
				getInBetweenAbsoluteY(absoluteAngleZAnt,absoluteAngleZ,absoluteAngleZPos,
						absoluteAngleYAnt,absoluteAngleY,absoluteAngleYPos,
						relativeAngleZAnt,
						relativeAngleY,
						relativeAngleX)
		);
	}
	private Pele getInBetweenAbsoluteY(		//COMPARA DUAS PELES EM ABS-Y
			double absoluteAngleZAnt,double absoluteAngleZ,double absoluteAngleZPos,
			double absoluteAngleYAnt,double absoluteAngleY,double absoluteAngleYPos,
			double relativeAngleZ,
			double relativeAngleY,
			double relativeAngleX){
		return Pele.getInBetweenAbsoluteY(
				getInBetweenAbsoluteZ(absoluteAngleZAnt,absoluteAngleZ,absoluteAngleZPos,
						absoluteAngleYAnt,
						relativeAngleZ,
						relativeAngleY,
						relativeAngleX),
				absoluteAngleY,
				getInBetweenAbsoluteZ(absoluteAngleZAnt,absoluteAngleZ,absoluteAngleZPos,
						absoluteAngleYPos,
						relativeAngleZ,
						relativeAngleY,
						relativeAngleX)
		);
	}
	private Pele getInBetweenAbsoluteZ(	//COMPARA DUAS PELES EM ABS-Z
			double absoluteAngleZAnt,double absoluteAngleZ,double absoluteAngleZPos,
			double absoluteAngleY,
			double relativeAngleZ,
			double relativeAngleY,
			double relativeAngleX){
		return Pele.getInBetweenAbsoluteZ(
				getRelativeAngleX(absoluteAngleZAnt,absoluteAngleY,relativeAngleZ,relativeAngleY,relativeAngleX),
				absoluteAngleZ,
				getRelativeAngleX(absoluteAngleZPos,absoluteAngleY,relativeAngleZ,relativeAngleY,relativeAngleX)
		);
	}
	public void draw(Graphics2D imagemEdit){
		final Pele pele=getPeleRotated();
		if(pele==null)return;
		pele.draw(imagemEdit);
	}
}