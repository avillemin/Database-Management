import java.util.ArrayList;
import java.util.Arrays;

public class KdTree
{
//	int test=0;
//	int test2=0;
	KdNode root;
//	long time1;
//	long time2;
//	long time3;
//	long time4;
//	long time;
//	long time5;
//	long time6;
//	int last;
	double dist1;
	double dist2;
	
	
	public KdTree() 
	{
		root = null;
	}
	
	public KdNode createkdtree (double[][] pointList, ArrayList<Integer> index, int depth, int dim, KdNode parent)
	{
		KdNode node =createkdtree2(pointList,index,depth,dim,parent);
		prepare(root,dim);
		return node;
	}
	
	public void prepare(KdNode node, int dim) {
		node.iniRect(dim, root);
		if (node.Left!=null) {
			prepare(node.Left,dim);
		}
		if (node.Right!=null) {
			prepare(node.Right,dim);	
		}
	}
	
	public KdNode createkdtree2 (double[][] pointList, ArrayList<Integer> index, int depth, int dim, KdNode parent)
	{
	    // Select axis based on depth so that axis cycles through all valid values
	    int axis = (depth % dim);
	    KdNode node;  
	    // Sort point list and choose median as pivot element
	    double median = findMedian(pointList,index,axis);
	        
	    // Create node and construct subtree
	    ArrayList<Integer> beforeMedian= new ArrayList<Integer>();
	    ArrayList<Integer> afterMedian= new ArrayList<Integer>();
	    node=new KdNode(axis,dim);
	    
	    for (int i=0; i<index.size();i++) {
	    	if (pointList[axis][index.get(i)]==median)
	    	{
	    		double[] liste = new double[pointList.length];
	    		for (int j=0;j<pointList.length;j++)
	    		{
	    			liste[j]=pointList[j][index.get(i)];
	    		}  		
	    		if (root==null) {
	    			root=node;
	    		}
	    		node.iniKdNode(liste,axis,dim,parent,this.root);
	    		
	    	}
	    	
	    	else if (pointList[axis][index.get(i)]<median)
	    	{
	    		beforeMedian.add(index.get(i));
	    	}
	    	else
	    	{
	    		afterMedian.add(index.get(i));
	    	}   
	    }

	    if (beforeMedian.isEmpty())
	    {
	    	node.Left=null;
	    }
	    else
	    {
	    	node.Left = createkdtree2(pointList,beforeMedian,depth+1,dim,node);
	    }
	    
	    if (afterMedian.isEmpty())
	    {
	    	node.Right=null;
	    }
	    else
	    {
	    	node.Right = createkdtree2(pointList,afterMedian, depth+1,dim,node);
	    }
	    
	    return node;
	}
	
	public static double findMedian(double[][] pointList, ArrayList<Integer> index,int dim) 
	{
		int size=0;
		double[] numArray = new double[index.size()];
		for (int i=0;i<index.size();i++) {
			numArray[size]=(pointList[dim][index.get(i)]);
			size++;
		}
		Arrays.sort(numArray);
		double median;
		median = (double) numArray[(int) numArray.length/2];
		return median;
	}
	
	public ArrayList<KdNode> knnSearch(double[] point,int dim, int knn)
	{
		ArrayList<KdNode> answerSetNode;
		if (dim<14) {
			answerSetNode=knnBestFirst(point,dim,knn);
		}
		else {
			answerSetNode=sequential(point,dim,knn);
		}
		
		return answerSetNode;
	}
	
	public ArrayList<KdNode> knnBestFirst(double[] point,int dim, int knn)
	{
//		time=time1=time2=time3=time4=time5=time6=last=0;
//		test=0;
		ArrayList<KdNode> candidateNode = new ArrayList<KdNode>();
		ArrayList<Double> candidateDistance = new ArrayList<Double>();
		candidateNode.add(root);
		candidateDistance.add(0.0);
		ArrayList<Double> answerSetDistance = new ArrayList<Double>();
		ArrayList<KdNode> answerSetNode = new ArrayList<KdNode>();
		
		return bestFirst(point,candidateDistance,candidateNode,answerSetDistance,answerSetNode,dim,knn);
	}

// ----------------------------------------- Best First --------------------------------------------------	
	
	public ArrayList<KdNode> bestFirst(double[] point,ArrayList<Double> candidateDistance,ArrayList<KdNode> candidateNode,ArrayList<Double> answerSetDistance,ArrayList<KdNode> answerSetNode, int dim,int knn)
	{
//		test++;
		KdNode nodeCandidat = candidateNode.get(0);
		candidateNode.remove(0);
		
		double dist=candidateDistance.get(0);
		candidateDistance.remove(0);		
		
		if (answerSetDistance.size()==0) {
			double sum=nodeCandidat.l2Distance(point,dim);
			answerSetDistance.add(sum);
			answerSetNode.add(nodeCandidat);
			
			double dist1=minDistance(point,nodeCandidat.Right,dim);
			double dist2=minDistance(point,nodeCandidat.Left,dim);
			
			if (dist1>dist2) {
				candidateNode.add(0,nodeCandidat.Right);
				candidateDistance.add(0,dist1);
				candidateNode.add(0,nodeCandidat.Left);
				candidateDistance.add(0,dist2);
			}	
			else {
				candidateNode.add(0,nodeCandidat.Left);
				candidateDistance.add(0,dist2);
				candidateNode.add(0,nodeCandidat.Right);
				candidateDistance.add(0,dist1);
			}
		}
		
		else if (dist<answerSetDistance.get(0) || answerSetDistance.size()!=knn) 
		{	
			double sum=nodeCandidat.l2Distance(point,dim);
			
			if (sum<answerSetDistance.get(0) || answerSetDistance.size()!=knn) {
//				time=System.nanoTime();
				addAnswer(sum, answerSetDistance, answerSetNode, nodeCandidat,knn);
//				time1=time1+System.nanoTime()-time;
//				time=System.nanoTime();
//				if (last<test) {last=test;}
//				time2=time2+System.nanoTime()-time;
			}

				if (nodeCandidat.Right!=null) {
//					time=System.nanoTime();
					dist1=minDistance(point,nodeCandidat.Right,dim);
//					time3=time3+System.nanoTime()-time;
					
					if (dist1<answerSetDistance.get(0) || answerSetDistance.size()!=knn)
					{
//						time=System.nanoTime();
						addValue(dist1,candidateDistance,candidateNode,nodeCandidat.Right);
//						test=test+1;
					}
				}
				
				if (nodeCandidat.Left!=null) {
//					time=System.nanoTime();
					dist2=minDistance(point,nodeCandidat.Left,dim);
//					time3=time3+System.nanoTime()-time;
					if ((dist2<answerSetDistance.get(0) || answerSetDistance.size()!=knn) && nodeCandidat.Left!=null)
					{		
						addValue(dist2,candidateDistance,candidateNode,nodeCandidat.Left);
//						test=test+1;					
					}
				}
		}
		if (candidateNode.size()!=0 && candidateDistance.get(0)<answerSetDistance.get(0)) {
				bestFirst(point,candidateDistance,candidateNode,answerSetDistance,answerSetNode,dim,knn);	
			}
		else {
//			System.out.print("Time1       : ");
//			System.out.println(time1);
//			System.out.print("minDistance : ");
//			System.out.println(time3);
//			System.out.print("addValue    : ");
//			System.out.println(time4);
//			System.out.print("Test : ");
//			System.out.println(test);
//			System.out.println(answerSetNode.toString());
//			System.out.println(candidateDistance.toString());
//			System.out.println(answerSetDistance.get(0));
//			System.out.print("Last : ");
//			System.out.println(last);
		}
		return candidateNode;
	}
	
	public ArrayList<KdNode> sequential(double[] point,int dim,int knn)
	{
//		time6=time5=0;
		ArrayList<KdNode> candidateNode = new ArrayList<KdNode>();
		candidateNode.add(root);
		ArrayList<Double> answerSetDistance = new ArrayList<Double>();
		ArrayList<KdNode> answerSetNode = new ArrayList<KdNode>();
		answerSetNode = sequentialSearch(point,root,answerSetDistance,answerSetNode,dim,knn);
//		System.out.println("2");
//		System.out.println(Arrays.toString(answerSetNode.toArray()));
//		System.out.println(test2);
//		System.out.print("Time6       : ");
//		System.out.println(time6);
//		System.out.print("Time5       : ");
//		System.out.println(time5);
		return answerSetNode;
	}
	
	public ArrayList<KdNode> sequentialSearch(double[] point,KdNode node,ArrayList<Double> answerSetDistance,ArrayList<KdNode> answerSetNode, int dim,int knn)
	{
//		test2=test2+1;
		KdNode nodeCandidat = node;
//		time=System.nanoTime();
		double dist=nodeCandidat.l2Distance(point,dim);
//		time6=time6+System.nanoTime()-time;
		if (answerSetDistance.size()==0) {
			answerSetDistance.add(dist);
			answerSetNode.add(nodeCandidat);
		}
		else if (dist<=answerSetDistance.get(0) || answerSetDistance.size()!=knn) 
		{	
//			time=System.nanoTime();
			addAnswer(dist, answerSetDistance, answerSetNode, nodeCandidat,knn);
//			time5=time5+System.nanoTime()-time;
		}
		if (nodeCandidat.Right!=null) { sequentialSearch(point,nodeCandidat.Right,answerSetDistance,answerSetNode,dim,knn); }
		if (nodeCandidat.Left!=null) { sequentialSearch(point,nodeCandidat.Left,answerSetDistance,answerSetNode,dim,knn); }

		return answerSetNode;
	}
	
	public double minDistance(double[] point, KdNode currentNode, int dim) {
//		time5=time5+System.nanoTime()-time;
		double sum =0;
//		time=System.nanoTime();
		for (int i=0;i<point.length;i++)
		{
			if (point[i]<currentNode.rect[0][i])
			{
				sum=sum+Math.pow(currentNode.rect[0][i]-point[i],2);
			}
			else if (point[i]>currentNode.rect[1][i]) {
				sum=sum+Math.pow(point[i]-currentNode.rect[1][i],2);
			}			
		}	
//		time6=time6+System.nanoTime()-time;
		return Math.sqrt(sum);
		
	}
	
	public void addValue(double distance,ArrayList<Double> candidateDistance,ArrayList<KdNode> candidateNodes,KdNode candidateNode) {
		int a = 0;
		int b = candidateNodes.size()-1;
		if (candidateNodes.size()==0)
		{
			candidateNodes.add(0,candidateNode);
			candidateDistance.add(distance);
		}
		else {			
			int c;
			if (distance>=candidateDistance.get(b)) {
				candidateNodes.add(candidateNode);
				candidateDistance.add(distance);
			}
			else {
				while (b-a>4) {
					c=(a+b)/2;
//					System.out.println(c);
					if (candidateDistance.get(c)>distance) {
						b=c;
					}
					else {
						a=c;
					}
				}
				while (a!=b+1) {
					if(distance<candidateDistance.get(a)) {
						candidateNodes.add(a,candidateNode);
						candidateDistance.add(a,distance);
						a=b;
					}
					a++;
				}
			}
		}
	}
	
	public void addAnswer(double distance,ArrayList<Double> answerSetDistance,ArrayList<KdNode> answerSetNode,KdNode candidateNode,int knn) {
		int stop=0;
		int a = 0;
		int b = answerSetDistance.size()-1;
		
		if (answerSetDistance.size()==0)
		{
			answerSetNode.add(candidateNode);
			answerSetDistance.add(distance);
		}
		
		else {			
			int c;
			if (distance<answerSetDistance.get(b)) {
				answerSetNode.add(candidateNode);
				answerSetDistance.add(distance);
			}
			else if (distance==answerSetDistance.get(b)) {
				int i=0;
				while (i<candidateNode.x.length) {
					if (candidateNode.x[i]==answerSetNode.get(b).x[i]) {i++;}
					else if (candidateNode.x[i]<answerSetNode.get(b).x[i]) {
						answerSetNode.add(candidateNode);
						answerSetDistance.add(distance);
						i=candidateNode.x.length+1;
						}
					else {
						answerSetNode.add(b,candidateNode);
						answerSetDistance.add(b,distance);
						i=candidateNode.x.length+1;
					}
					if (i==candidateNode.x.length && candidateNode.x[i]==answerSetNode.get(b).x[i])
					{
						answerSetNode.add(candidateNode);
						answerSetDistance.add(distance);
					}
				}
			}
			else {
				while (b-a>2) {
					c=(a+b)/2;
//					System.out.println(c);
					if (answerSetDistance.get(c)<distance) {
						b=c;
					}
					else if (answerSetDistance.get(c)>distance) {
						a=c;
					}
					else {
						breakTie(c, distance, answerSetDistance, answerSetNode, candidateNode);
						stop=1;
					}
				}
				while (a!=b+1 && stop==0) {
					if(distance>answerSetDistance.get(a)) {
						answerSetNode.add(a,candidateNode);
						answerSetDistance.add(a,distance);
						a=b;
					}
					else if (distance==answerSetDistance.get(a)) {
						breakTie(a, distance, answerSetDistance, answerSetNode, candidateNode);
						a=b;
					}
					a++;
				}
			}
		}
		if (answerSetDistance.size()>knn)
			{
				answerSetDistance.remove(0);
				answerSetNode.remove(0);
			}
	}
	
	public void breakTie(int index,double distance,ArrayList<Double> answerSetDistance,ArrayList<KdNode> answerSetNode,KdNode candidateNode) {
		if (distance!=answerSetDistance.get(index)) {
			answerSetNode.add(index,candidateNode);
			answerSetDistance.add(index,distance);
		}
		else {
			int i=0;
			while (i<candidateNode.x.length) {
				if (candidateNode.x[i]==answerSetNode.get(index).x[i]) {i++;}
				else if (candidateNode.x[i]<answerSetNode.get(index).x[i]) {
					answerSetNode.add(index+1,candidateNode);
					answerSetDistance.add(index+1,distance);
					i=candidateNode.x.length+1;
					}
				else {				
					answerSetNode.add(index,candidateNode);
					answerSetDistance.add(index,distance);
					i=candidateNode.x.length+1;
					
				}
				if (i==candidateNode.x.length && candidateNode.x[i]==answerSetNode.get(index).x[i])
				{
					answerSetNode.add(index,candidateNode);
					answerSetDistance.add(index,distance);
				}
			}
		}
	}
}
