package ch.uzh.ifi.csg.cloudsim.rda.experiments;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.power.PowerVmAllocationPolicySimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;

import ch.uzh.ifi.csg.cloudsim.rda.RdaHost;
import ch.uzh.ifi.csg.cloudsim.rda.greediness.VmSchedulerGreedinessAllocationAlgorithm;
import ch.uzh.ifi.csg.cloudsim.rda.provisioners.BwProvisionerSimple;
import ch.uzh.ifi.csg.cloudsim.rda.provisioners.RamProvisionerSimple;
import ch.uzh.ifi.csg.cloudsim.rda.provisioners.StorageIOProvisionerSimple;
import ch.uzh.ifi.csg.cloudsim.rda.useraware.RdaHostUserAware;
import ch.uzh.ifi.csg.cloudsim.rda.useraware.UserAwareDatacenter;

/**
 * 
 * @author pat
 *
 */
public class UserAwareExperimentalSuite extends ExperimentalSuite {

	/** path to the python binary on your system */
	private String pythonPath = "C:\\Program Files (x86)\\Python34\\python";

	/**
	 * Main method to run this experiment
	 *
	 * @param args
	 *            the args
	 */
	public static void main(String[] args) {

		UserAwareExperimentalSuite suite = new UserAwareExperimentalSuite();
		// VMs and Hosts to create
		suite.simulate(2, 3, 3);

	}

	@Override
	public Datacenter createDatacenter(String name, int hostCnt) {

		List<Host> hostList = new ArrayList<Host>();

		for (int i = 0; i < hostCnt; i++) {
			hostList.add(createHost(i)); // This
		}

		String arch = "x86"; // system architecture
		String os = "Linux"; // operating system
		String vmm = "Xen";
		double time_zone = 10.0; // time zone this resource located
		double cost = 3.0; // the cost of using processing in this resource
		double costPerMem = 0.05; // the cost of using memory in this resource
		double costPerStorage = 0.001; // the cost of using storage in this
										// resource
		double costPerBw = 0.0; // the cost of using bw in this resource
		LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are
																		// not
																		// adding
																		// SAN
		// devices by now

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
				arch, os, vmm, hostList, time_zone, cost, costPerMem,
				costPerStorage, costPerBw);

		Datacenter datacenter = null;
		try {
			datacenter = new UserAwareDatacenter(name, characteristics,
					new PowerVmAllocationPolicySimple(hostList), storageList,
					schedulingInterval);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;
	}

	@Override
	public RdaHost createHost(int hostId) {
		List<Pe> peList = new ArrayList<Pe>();
		int mips = 1000;
		peList.add(new Pe(0, new PeProvisionerSimple(mips)));
		int ram = 2048; // host memory (MB)
		long storage = 1000000; // host storage (MB)
		int bw = 1000; // MBit/s
		int storageIO = 10000;

		RamProvisionerSimple ramProvisioner = new RamProvisionerSimple(ram);
		BwProvisionerSimple bwProvisioner = new BwProvisionerSimple(bw);
		StorageIOProvisionerSimple storageIOProvisioner = new StorageIOProvisionerSimple(
				storageIO);
		return new RdaHostUserAware(hostId, ramProvisioner, bwProvisioner,
				storageIOProvisioner, storage, peList,
				new VmSchedulerGreedinessAllocationAlgorithm(peList,
						ramProvisioner, bwProvisioner, storageIOProvisioner,
						pythonPath), scarcitySchedulingInterval);

	}

	public String getPythonPath() {
		return pythonPath;
	}

	public void setPythonPath(String pythonPath) {
		this.pythonPath = pythonPath;
	}
}