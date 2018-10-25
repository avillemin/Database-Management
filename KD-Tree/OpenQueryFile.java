import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class OpenQueryFile {
	
	public static void main(String[] args) throws IOException
	{
//__1_<dataset_file>

//Scan input (path dataset_file)
        Scanner scan1 = new Scanner(System.in);
        String dataset_path = scan1.nextLine();
//Read dataset_file      
        BufferedReader readBuffer = null;
        String line;
        StringBuilder dataset_file = new StringBuilder();
        try{readBuffer = new BufferedReader(new FileReader(dataset_path));}
        catch(FileNotFoundException exc){System.out.println("Error");}
        while ((line = readBuffer.readLine()) != null)
        dataset_file.append(line+" ");
        readBuffer.close();
//ConvertToDouble[][]                
		List<String> list = Arrays.asList(dataset_file.toString().split(" "));
		int D = Integer.parseInt(list.get(0));	
		int N1 = Integer.parseInt(list.get(1));
		
		double[][] dataset = new double[D][N1];
		for(int a=2; a<D*N1+2;a++){
			dataset[(a-2)%D][(a-2)/D] = (double) (Double.parseDouble(list.get(a)));
		}
//CreateTree
		KdTree tree = new KdTree();
		KdNode parent = new KdNode(0,D);
		ArrayList<Integer> index = new ArrayList<Integer>();
		for (int k=0;k<N1;k++){index.add(k);}
		tree.createkdtree(dataset, index, 0, D, parent);
		dataset=null;
		
		System.out.println(0);
		
//__2_<query_file>
		
//Scan input (path query_file)	
        Scanner scan2 = new Scanner(System.in);
        String query_path = scan2.nextLine();
//Read query_file    
        BufferedReader readBuffer2 = null;
        String line2;
        StringBuilder query_file = new StringBuilder();
        try{readBuffer2 = new BufferedReader(new FileReader(query_path));}
        catch(FileNotFoundException exc){System.out.println("Error");}
        while ((line2 = readBuffer2.readLine()) != null)
        query_file.append(line2+" ");
        readBuffer2.close();
//ConvertToDouble[][]
		List<String> queriesList = Arrays.asList(dataset_file.toString().split(" "));
		int N2 = Integer.parseInt(queriesList.get(1));
		double[][] querieset = new double[N2][D];
		for(int b=2; b<D*N2+2;b++){
			querieset[(b-2)/D][(b-2)%D] = (double) (Double.parseDouble(queriesList.get(b)));
		}
//Scan for k (k-NN)
        Scanner scan3 = new Scanner(System.in);
        int k = scan3.nextInt();
        scan3.close();
		
// k-NN search
//__3_results.txt
		PrintWriter writer = new PrintWriter("results.txt", "UTF-8");
		
		for (int c=1;c<N2;c++) {
			double[] querie =querieset[c	];
			ArrayList<KdNode> answerSetNode = tree.knnSearch(querie,D,k);
			
			for (int j=0;j<answerSetNode.size();j++) {
				double[] point = answerSetNode.get(j).x;
				String pointStr= new String();
				for (int p=0;p<D;p++){
					if (p+1<D){pointStr += point[p]+" ";}
						else {pointStr += point[p];}
				}
				writer.println(pointStr);
			}
//			tree.sequential(querie,D,k);
			}
		writer.close();
		System.out.println(1);
		
	}
}
