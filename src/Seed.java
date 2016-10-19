//my 7 dimensional point graph
//Shameer Khan
//CS-381
import java.io.PrintWriter;
import java.util.Scanner;

public class Seed {
	
	private double area;
	private double perimeter;
	private double compactness;
	private double widthOfKernel;
	private double lengthOfKernel;
	private double aCoefficient;
	private double lengthOfGroove;
	private int member;
	private int newMember;
	public int getNewMember() {
		return newMember;
	}

	public void setNewMember(int newMember) {
		this.newMember = newMember;
	}

	private int personalIndex;//this one i kept for debugging purpose
	private static double personalDistance;//for debugging purpose
	
	public Seed(){};//default empty constructor
	
	public Seed(String line){//main constructor assigning the 7-dimensional points .
		Scanner scan = new Scanner(line);
			area=Double.parseDouble(scan.next());
			perimeter=Double.parseDouble(scan.next());
			compactness=Double.parseDouble(scan.next());
			lengthOfKernel=Double.parseDouble(scan.next());
			widthOfKernel=Double.parseDouble(scan.next());
			aCoefficient=Double.parseDouble(scan.next());
			lengthOfGroove=Double.parseDouble(scan.next());
			//member=Integer.parseInt(scan.next());
		scan.close();
	}
	//getters and setters 
	public int getMember() {
		return member;
	}

	public void setMember(int member) {
		this.member = member;
	}

	public int getPersonalIndex() {
		return personalIndex;
	}

	public void setPersonalIndex(int personalIndex) {
		this.personalIndex = personalIndex;
	}

	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public double getPerimeter() {
		return perimeter;
	}
	public void setPerimeter(double perimeter) {
		this.perimeter = perimeter;
	}
	public double getCompactness() {
		return compactness;
	}
	public void setCompactness(double compactness) {
		this.compactness = compactness;
	}
	public double getWidthOfKernel() {
		return widthOfKernel;
	}
	public void setWidthOfKernel(double widthOfKernel) {
		this.widthOfKernel = widthOfKernel;
	}
	public double getLengthOfKernel() {
		return lengthOfKernel;
	}
	public void setLengthOfKernel(double lengthOfKernel) {
		this.lengthOfKernel = lengthOfKernel;
	}
	public double getaCoefficient() {
		return aCoefficient;
	}
	public void setaCoefficient(double aCoefficient) {
		this.aCoefficient = aCoefficient;
	}
	public double getLengthOfGroove() {
		return lengthOfGroove;
	}
	public void setLengthOfGroove(double lengthOfGroove) {
		this.lengthOfGroove = lengthOfGroove;
	}
	
	public static int grantMembership(Seed a[], Seed c1, Seed c2, Seed c3, int i, int K){//granting membership to each kernel/seed
		int location=0;
		double min[]= new double [3];
		//finds the distance between the 3 clusters to see which one is smaller
		min[0]=findDistance(a,c1,i);
		min[1]=findDistance(a,c2,i);
		min[2]=findDistance(a,c3,i);
		double minimum=min[0];
		for(int j=0;j<K;j++){//finding smallest/closest comparison is done here
			if(min[j]<minimum){
				minimum=min[j];
				location=j;
			}
		}
		return (location+1);
	}
	
	public static double findDistance(Seed a[], Seed c1, int i){//calculating distance from center with this function
		double distance=0;
		double sum=0;
		double data[]= new double[7];
		data[0]=(c1.area-a[i].area);
		data[1]=(c1.perimeter-a[i].perimeter);
		data[2]=(c1.compactness-a[i].compactness);
		data[3]=(c1.lengthOfKernel-a[i].lengthOfKernel);
		data[4]=(c1.widthOfKernel-a[i].widthOfKernel);
		data[5]=(c1.aCoefficient-a[i].aCoefficient);
		data[6]=(c1.lengthOfGroove-a[i].lengthOfGroove);
		for(int j=0; j<data.length;j++){
			sum= sum+ (data[j]*data[j]);
		}
		distance=Math.sqrt(sum);
		personalDistance=distance;//for debugging purpose
		return distance;
	}
	
	public static double getPersonalDistance() {
		return personalDistance;
	}

	public static void setPersonalDistance(double personalDistance) {
		Seed.personalDistance = personalDistance;
	}

	public static Seed newCentroid( Seed c1[] ){//this will be calculating new center from each cluster
		double area=0, perimeter=0,compactness=0, width=0, length=0, lk=0, ac=0;
		boolean flag=false;
		for(int i=0;i<c1.length;i++){//here adding up all the 7 characteristics of our point in graph
			if(c1[i]!=null){
			area+=c1[i].area;
			perimeter+=c1[i].perimeter;
			compactness+=c1[i].compactness;
			length+=c1[i].lengthOfKernel;
			width+=c1[i].widthOfKernel;
			ac+=c1[i].aCoefficient;
			lk+=c1[i].lengthOfGroove;
			flag=true;
			}
		}
		Seed s1= new Seed();
		if(flag==true){
		s1.area=(area/c1.length);//no dividing them to find the average for each 7 points
		s1.compactness=(compactness/c1.length);
		s1.perimeter=(perimeter/c1.length);
		s1.lengthOfKernel=(length/c1.length);
		s1.widthOfKernel=(width/c1.length);
		s1.aCoefficient=(ac/c1.length);
		s1.lengthOfGroove=(lk/c1.length);
		}
		return s1;//return my new center
	}
	
	public static boolean isSimilar(Seed newC1, Seed oldC1 ){//this is the judge function which will decide if old center and new center are same
		if(newC1.area==oldC1.area &&
		newC1.perimeter==oldC1.perimeter &&
		newC1.compactness==oldC1.compactness &&
		newC1.widthOfKernel==oldC1.widthOfKernel &&
		newC1.lengthOfKernel==oldC1.lengthOfKernel &&
		newC1.lengthOfGroove == oldC1.lengthOfGroove &&
		newC1.aCoefficient == oldC1.aCoefficient){
			return true;
		}
		return false;
	}
	
	public static void print(Seed c1[],PrintWriter p){//print the whole cluster and all its points
		p.println();
		int count=1;
		for(int i=0;i<c1.length;i++){
			if(c1[i]!=null){
			p.print("S."+c1[i].personalIndex+"\t"+c1[i].area+ "\t"+c1[i].perimeter+"\t"+c1[i].getCompactness()+"\t"+c1[i].getLengthOfKernel()+"\t"+c1[i].getWidthOfKernel()+"\t"+c1[i].aCoefficient+"\t"+c1[i].lengthOfGroove+"\t"+c1[i].getMember());
			p.println();
			count++;
			}
		}
		p.println("current cluster size: "+ (count-1));
		p.println();
	}
	
	public static double calculateIV(Seed c1[], Seed centroid){//calculate IV
		double sum=0.0;
		double total=0;
		for(int i=0;i<c1.length;i++){
			if(c1[i]!=null)
			{
				sum+=((c1[i].area-centroid.area)*(c1[i].area-centroid.area));
				sum+=((c1[i].perimeter-centroid.perimeter)*(c1[i].perimeter-centroid.perimeter));
				sum+=((c1[i].compactness-centroid.compactness)*(c1[i].compactness-centroid.compactness));
				sum+=((c1[i].widthOfKernel-centroid.widthOfKernel)*(c1[i].widthOfKernel-centroid.widthOfKernel));
				sum+=((c1[i].lengthOfKernel-centroid.lengthOfKernel)*(c1[i].lengthOfKernel-centroid.lengthOfKernel));
				sum+=((c1[i].aCoefficient-centroid.aCoefficient)*(c1[i].aCoefficient-centroid.aCoefficient));
				sum+=((c1[i].lengthOfGroove-centroid.lengthOfGroove)*(c1[i].lengthOfGroove-centroid.lengthOfGroove));
				total+=Math.sqrt(sum);
			}
		}
		
		return total;
		}
	//the point of this method is for debug purpose so i can track the centroids between old and new.
	public static void printCenter(Seed Oldcentroid1, Seed Oldcentroid2, Seed Oldcentroid3, Seed newCentroid1, Seed newCentroid2, Seed newCentroid3){
		System.out.print("Old centroid 1= ("+Oldcentroid1.area+ "\t"+Oldcentroid1.perimeter+"\t"+Oldcentroid1.getCompactness()+"\t"+Oldcentroid1.getWidthOfKernel()+"\t"+Oldcentroid1.getLengthOfKernel()+"\t"+Oldcentroid1.getaCoefficient()+"\t"+Oldcentroid1.getLengthOfGroove()+")\n");
		System.out.print("New centroid 1= ("+newCentroid1.area+ "\t"+newCentroid1.perimeter+"\t"+newCentroid1.getCompactness()+"\t"+newCentroid1.getWidthOfKernel()+"\t"+newCentroid1.getLengthOfKernel()+"\t"+newCentroid1.getaCoefficient()+"\t"+newCentroid1.getLengthOfGroove()+")\n");
		System.out.print("Old centroid 2= ("+Oldcentroid2.area+ "\t"+Oldcentroid2.perimeter+"\t"+Oldcentroid2.getCompactness()+"\t"+Oldcentroid2.getWidthOfKernel()+"\t"+Oldcentroid2.getLengthOfKernel()+"\t"+Oldcentroid2.getaCoefficient()+"\t"+Oldcentroid2.getLengthOfGroove()+")\n");
		System.out.print("New centroid 2= ("+newCentroid2.area+ "\t"+newCentroid2.perimeter+"\t"+newCentroid2.getCompactness()+"\t"+newCentroid2.getWidthOfKernel()+"\t"+newCentroid2.getLengthOfKernel()+"\t"+newCentroid2.getaCoefficient()+"\t"+newCentroid2.getLengthOfGroove()+")\n");
		System.out.print("Old centroid 3= ("+Oldcentroid3.area+ "\t"+Oldcentroid3.perimeter+"\t"+Oldcentroid3.getCompactness()+"\t"+Oldcentroid3.getWidthOfKernel()+"\t"+Oldcentroid3.getLengthOfKernel()+"\t"+Oldcentroid3.getaCoefficient()+"\t"+Oldcentroid3.getLengthOfGroove()+")\n");
		System.out.print("New centroid 3= ("+newCentroid3.area+ "\t"+newCentroid3.perimeter+"\t"+newCentroid3.getCompactness()+"\t"+newCentroid3.getWidthOfKernel()+"\t"+newCentroid3.getLengthOfKernel()+"\t"+newCentroid3.getaCoefficient()+"\t"+newCentroid3.getLengthOfGroove()+")\n");
	}
	
	public static double basicDistance(Seed s, Seed x){//a function that will help me count distance everytime for EV
		double total=0;
		double sum=0;
		
		sum+=(s.area-x.area)*(s.area-x.area);
		sum+=(s.perimeter-x.perimeter)*(s.perimeter-x.perimeter);
		sum+=(s.compactness-x.compactness)*(s.compactness-x.compactness);
		sum+=(s.widthOfKernel-x.widthOfKernel)*(s.widthOfKernel-x.widthOfKernel);
		sum+=(s.lengthOfKernel-x.lengthOfKernel)*(s.lengthOfKernel-x.lengthOfKernel);
		sum+=(s.aCoefficient-x.aCoefficient)*(s.aCoefficient-x.aCoefficient);
		sum+=(s.lengthOfGroove-x.lengthOfGroove)*(s.lengthOfGroove-x.lengthOfGroove);
		
		total=Math.sqrt(sum);
		
		return total;
	}
	
	public static double findSeperateDistance(Seed cluster1[], Seed cluster2[]){//calculate the distance of elements in a cluster from others
		double distance1=0;
		for(int i=0;i<cluster1.length;i++){
			if(cluster1[i]!=null)
			for(int j=0;j<cluster2.length;j++){
				if(cluster2[j]!=null){
				distance1+=basicDistance(cluster1[i],cluster2[j]);
				}
			
			}
		}
		return distance1;
	}
	
	public static double calculateEV(Seed c1[], Seed c2[], Seed c3[]){//this function checks distance of elements in a cluster from other clusters and find EV
		double d1=0, d2=0, d3=0;
		d1+=findSeperateDistance(c1,c2);
		d1+=findSeperateDistance(c1,c3);
		d2+=findSeperateDistance(c2,c1);
		d2+=findSeperateDistance(c2,c3);
		d3+=findSeperateDistance(c3,c1);
		d3+=findSeperateDistance(c3,c2);
		
		double EV=(d1+d2+d3)/210;
		
		return EV;
	}
	
	public static boolean areOriginalPointsIntact(Seed a[]){// this checks if members have been switched from last configuration if so convergence hasn't been reached.
	for(int i=0;i<a.length;i++){
		if(a[i].member!=a[i].newMember)
			return false;
	}
		return true;
	}
}
