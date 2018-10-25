public class KdNode {
	int axis;
    double[] x;
//    double distance;
    double[][] rect;
 
    KdNode Parent;
    KdNode Left;
    KdNode Right;
 
    public KdNode(int axis0, int dim)
    {
        x = new double[dim];
        axis = axis0; 
        Left = Right = Parent = null;
        rect= new double[2][dim];
		for (int i=0;i<dim;i++) {
			rect[0][i]=0;
			rect[1][i]=1;
		}
        
    }
    
    public void iniKdNode(double[] x0, int axis0, int dim,KdNode parent,KdNode root)
    {
        for (int k = 0; k < dim; k++) 
        {
            x[k] = x0[k];
        }
        Parent=parent;
    }
    
    public double l2Distance(double[] point,int dim) {
    	double sum=0;
		for (int i=0;i<dim;i++)
		{
			sum=sum+Math.pow(point[i]-x[i],2);
		}
		return Math.sqrt(sum);
    }
    
    public void iniRect(int dim,KdNode root) {

    	if (this!=root) {
    		for (int i=0;i<dim;i++) {
    			rect[0][i]=Parent.rect[0][i];
    			rect[1][i]=Parent.rect[1][i];
    		}
	    	
			if (this==Parent.Right){
				rect[0][Parent.axis]=Parent.x[Parent.axis];
			}
			else if (this==Parent.Left){
				rect[1][Parent.axis]=Parent.x[Parent.axis];
			}
			else {
//				System.out.println("Error");
			}
			}
    }
    
}
