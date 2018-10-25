import java.util.ArrayList;
import java.util.Arrays;


public class Main_q1_q2 {

	public static void main(String[] args)
	{
		int nbTest=100;
		int n=100000;
		int dim[] = new int[] {2,3,5,10,15,20};
		
		double[] bestFirstTime = new double[dim.length];
		double[] sequentialSearchTime = new double[dim.length];
		double[] ratioDistance = new double[dim.length];
		
		for(int ini=0;ini<dim.length;ini++) {
			bestFirstTime[ini]=0;
			sequentialSearchTime[ini]=0;
		}
		
		for (int d=0;d<dim.length;d++) {
			
			double[][] queries = new double[nbTest][dim[d]];
			for (int i=0; i<dim[d]; i++){
				for (int j=0; j<nbTest ;j++){
					queries[j][i] = (double) Math.random();
				}
			}
			
			double[][] points = new double[dim[d]][n];
			for (int i=0; i<dim[d]; i++) {
				for (int j=0; j<n; j++) {
					points[i][j]= (double) (Math.random());
				}
			}
			
			KdTree tree = new KdTree();
			KdNode parent = new KdNode(0,dim[d]);
			ArrayList<Integer> index = new ArrayList<Integer>();
			for (int i=0;i<n;i++){index.add(i);}
			tree.createkdtree(points, index, 0, dim[d], parent);
			points=null;
//			System.out.print("Dim : ");
//			System.out.println(dim[d]);
			for (int k=0;k<nbTest;k++) {
//				System.out.println(dim[d]);
				double[] querie =queries[k];
				double time1 = (double)System.currentTimeMillis();
				tree.knnBestFirst(querie,dim[d],20);
				double time2 = (double)System.currentTimeMillis();
				tree.sequential(querie,dim[d],20);
				double time3 = (double)System.currentTimeMillis();
				bestFirstTime[d]=bestFirstTime[d]+((time2-time1)/nbTest);
				sequentialSearchTime[d]=sequentialSearchTime[d]+((time3-time2)/nbTest);
				ArrayList<KdNode> answerSetNode = tree.sequential(querie,dim[d],100);
				ratioDistance[d]=ratioDistance[d]+((answerSetNode.get(98).l2Distance(querie,dim[d]))/(answerSetNode.get(0).l2Distance(querie,dim[d])))/nbTest;
			}
		}
		
		System.out.println("Time of the Best-First algorithm(q1)");
		System.out.println(Arrays.toString(bestFirstTime));
		System.out.println("Time of the sequential search(q1)");
		System.out.println(Arrays.toString(sequentialSearchTime));
		System.out.println("Ratio of the distances(q2)");
		System.out.println(Arrays.toString(ratioDistance));
		
	
	}
}
