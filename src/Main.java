//Shameer Khan
//CS-381
//Homework 1

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		int trial=1;
		while(trial!=6){//will run five trials simultaneously 
	
		try {
			BufferedReader bw= new BufferedReader (new FileReader(args[0]));
			Scanner scan= new Scanner(new FileReader (args[0]));
			PrintWriter pr= new PrintWriter(new FileWriter(args[1],true));
			pr.println("Trial no."+trial);
			String line= bw.readLine();
			int numofDataPts=0;//too create the size of the main array
			int K=3;//k should be 3
			boolean reached_convergence=false;
			Seed cluster1[],cluster2[],cluster3[];//3 clusters
			int c1Size=0;//potential sizes for our clusters will be assigned proper value later.
			int c2Size=0;
			int c3Size=0;
			Random rand = new Random();
			int generator= rand.nextInt(210);//get the location of 1st centeroid for initial use only
			int generator2= rand.nextInt(210);//second centroid for initial use only
			int generator3= rand.nextInt(210);//third centroid for initial use only
			while(scan.hasNext()){//checking how big my main array have to be
				numofDataPts++;
				scan.nextLine();
			}
			Seed collection [] = new Seed[numofDataPts];//main array holds all 210 seed members
			int i=0;
			while(line!=null){//assigning each seed object its 7 points
				collection [i]= new Seed(line);
				collection[i].setPersonalIndex(i);
				i++;
				line=bw.readLine();
			}
			Seed centroid1= collection[generator];//first center only for initial use
			Seed centroid2= collection[generator2];//second center only for initial use
			Seed centroid3= collection[generator3];//third center only for initial use
			centroid1.setMember(1);//giving them their membership only for the 3 centers
			centroid2.setMember(2);
			centroid3.setMember(3);
			for(int k=0; k<collection.length; k++){//granting all other 207 seed object their membership based on the their distance to the center
				if(k!=generator || k!=generator2 || k!=generator3){
				int x=Seed.grantMembership(collection, centroid1, centroid2, centroid3, k,K);
				collection[k].setMember(x);
				}
			}
			pr.println("original centers: ");//Printing original center
			printKcentroid(centroid1,pr);
			printKcentroid(centroid2,pr);
			printKcentroid(centroid3,pr);
			pr.println();
			c1Size=getClusterSize(collection,1);//this will tell me how big my cluster1 is
			c2Size=getClusterSize(collection,2);//how big cluster2 is 
			c3Size=getClusterSize(collection ,3);//how big cluster3 is
			cluster1= new Seed[c1Size+1];//cluster1 which contains all members belong to cluster1
			cluster2 = new Seed[c2Size+1];//cluster2 which will contain all it's members
			cluster3=  new Seed[c3Size+1];//cluster3 which will contain all it's members
			createCluster(collection, cluster1, cluster2, cluster3);//this will assign all seed objects from the main array to their corresponding cluster based on their membership
			Seed newCentroid1=null;
			Seed newCentroid2=null;
			Seed newCentroid3=null;
			Seed newCluster1[]=null, newCluster2[]=null, newCluster3[]=null;
			boolean flag1=false, flag2=false, flag3=false;//to find if I have reached convergence
			int counter=0;
			while(reached_convergence==false){	//keep looping until we found convergence
			newCentroid1 = Seed.newCentroid(cluster1);//will be new centroid1
			newCentroid2= Seed.newCentroid(cluster2);//will be new centroid2
			newCentroid3= Seed.newCentroid(cluster3);//will be new centroid3
			if(Seed.areOriginalPointsIntact(collection)==true){
				flag1=true;
			}
			if(Seed.areOriginalPointsIntact(collection)==true){
				flag2=true;
			}
			if(Seed.areOriginalPointsIntact(collection)==true){
				flag3=true;
			}
			if(flag1==true && flag2==true && flag3==true){//if all 3 centroids are same as their old counterparts we end loop Convergence reached.
				reached_convergence=true;
				pr.println("convergence reached!");
			}
			for(int k=0;k<collection.length;k++){
			int x= Seed.grantMembership(collection, newCentroid1, newCentroid2, newCentroid3, k, K);//we grant membership to all 210 seed Objects now based on the new centroids
			if(counter%2==0)
			collection[k].setNewMember(x);
			if(counter%2!=0)
				collection[k].setMember(x);
				}
			
			c1Size=getClusterSize(collection,1);//count the size for cluster1 again as points will be moved around
			c2Size=getClusterSize(collection,2);//count the new size for cluster2
			c3Size=getClusterSize(collection ,3);//count the new size for cluster3
			newCluster1=new Seed[c1Size+1];// resize the array
			newCluster2= new Seed[c2Size+1];//resize the array
			newCluster3 = new Seed[c3Size+1];//resize the array
			createCluster(collection,newCluster1,newCluster2,newCluster3);//recreate the cluster based on Seed objects new membership
			centroid1=newCentroid1;//here now oldcentroids are overwritten new ones as by the next 3 line we will recalculate center again and compare these 3.
			centroid2=newCentroid2;
			centroid3= newCentroid3;
			counter++;
			}
			pr.println("final centroids and cluster:");//printing final centroids
			printKcentroid(newCentroid1,pr);
			printKcentroid(newCentroid2,pr);
			printKcentroid(newCentroid3,pr);
			Seed.print(newCluster1,pr);//print the final cluster1 after reaching convergence
			Seed.print(newCluster2,pr);//print the final cluster2 after reaching convergence
			Seed.print(newCluster3,pr);//print the final cluster3 after reaching convergence
			double IV=Seed.calculateIV(cluster1, newCentroid1);
			//for the next lines we will calculate IV, EV and IV/EV(Optimization)
			IV+=Seed.calculateIV(cluster2, newCentroid2);
			IV+=Seed.calculateIV(cluster3, newCentroid3);
			double EV=Seed.calculateEV(newCluster1, newCluster2, newCluster3);
			pr.println("IV of the system is : "+ IV);
			pr.println("EV of the system is: "+EV);
			pr.println("Optimized K from IV/EV is: "+ (IV/EV));
			bw.close();
			scan.close();
			trial++;
			pr.println("\n");
			pr.flush();
			pr.close();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
		
		}

	
	private static void printKcentroid(Seed c1, PrintWriter pr) {//to print a Seed object
		pr.print(c1.getArea()+" "+c1.getPerimeter()+" "+c1.getCompactness()+" "+ c1.getLengthOfKernel()+" "+c1.getWidthOfKernel()+" "+c1.getaCoefficient()+" "+c1.getLengthOfGroove()+"\n");
		
	}


	private static int getClusterSize(Seed[] collection, int i) {//get the size for the cluster after each iteration as points get moved around
		int size=0;
		for(int j=0;j<collection.length;j++){
			if(collection[j].getMember()==i)
				size++;
		}
		return size;
	}

	public static void createCluster(Seed total[], Seed c1[], Seed c2[], Seed c3[]){//create each cluster and assign the seeds based on their member value
		int j=0,k=0,l=0;
		for (int i=0; i<total.length;i++){
			if(total[i].getMember()==1){
				c1[j]=total[i];
				j++;
			}
			else if(total[i].getMember()==2){
				c2[k]=total[i];
				k++;
			}
			else{
				c3[l]=total[i];
				l++;
			}
	
		}
	}

}
