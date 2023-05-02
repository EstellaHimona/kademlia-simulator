package peersim.kademlia;

import java.math.BigInteger;
import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;

/**
 * This control initializes the whole network (that was already created by peersim) assigning a
 * unique NodeId, randomly generated, to every node.
 *
 * @author Daniele Furlan, Maurizio Bonani
 * @version 1.0
 */
public class CustomDistribution implements peersim.core.Control {

  private static final String PAR_PROT = "protocol";
  private static final String PAR_PROT_EVIL_KAD = "protocolEvilkad";

  /* Protocol identifier for Kademlia nodes */
  private int protocolKadID;
  private int evilprotocolID;
  private UniformRandomGenerator urg;

  /* Declare number of malicious nodes */
  // private int numEvilNodes = (int) (Network.size() * 0.3);

  public CustomDistribution(String prefix) {
    protocolKadID = Configuration.getPid(prefix + "." + PAR_PROT);
    urg = new UniformRandomGenerator(KademliaCommonConfig.BITS, CommonState.r);
    evilprotocolID = Configuration.getPid(prefix + "." + PAR_PROT_EVIL_KAD);
  }

  /**
   * Scan over the nodes in the network and assign a randomly generated NodeId in the space
   * 0..2^BITS, where BITS is a parameter from the kademlia protocol (usually 160)
   *
   * @return boolean always false
   */
  public boolean execute() {

    int evil_share = Configuration.getInt("nodes" + "." + "evil_share");
    float evil_percent = (float) evil_share / 100;

    System.out.println("Evil share: " + evil_share);
    System.out.println("Evil percent: " + evil_percent);

    // set the number of evil nodes
    int numEvilNodes = (int) (Network.size() * evil_percent);
    System.out.println("Number of evil nodes: " + numEvilNodes);

    // BigInteger tmp;
    for (int i = 0; i < Network.size(); ++i) {
      Node generalNode = Network.get(i);
      BigInteger id;
      // BigInteger attackerID = null;
      KademliaNode node;
      // String ip_address;
      id = urg.generate();
      // node = new KademliaNode(id, randomIpAddress(r), 0);
      node = new KademliaNode(id, "0.0.0.0", 0);

      KademliaProtocol kadProt = null;

      /* Generate benign and malicious nodes */
      if ((i > 0) && (i < (1 + numEvilNodes))) {
        kadProt = ((KademliaMalicious) (Network.get(i).getProtocol(evilprotocolID)));
        kadProt.setProtocolID(evilprotocolID);
        node.setEvil(true);
      } else {
        kadProt = ((KademliaProtocol) (Network.get(i).getProtocol(protocolKadID)));
        kadProt.setProtocolID(protocolKadID);
      }
      kadProt.setNode(node);
      generalNode.setKademliaProtocol(kadProt);
    }

    return false;
  }
}
