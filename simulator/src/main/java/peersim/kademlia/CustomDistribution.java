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
  private UniformRandomGenerator urg;

  /* Protocol identifier of malicious Kademlia nodes */
  private int protocolEvilKadID;

  public CustomDistribution(String prefix) {
    protocolKadID = Configuration.getPid(prefix + "." + PAR_PROT);
    protocolEvilKadID = Configuration.getPid(prefix + "." + PAR_PROT_EVIL_KAD, protocolKadID);
    urg = new UniformRandomGenerator(KademliaCommonConfig.BITS, CommonState.r);
  }

  /**
   * Scan over the nodes in the network and assign a randomly generated NodeId in the space
   * 0..2^BITS, where BITS is a parameter from the kademlia protocol (usually 160)
   *
   * @return boolean always false
   */
  public boolean execute() {
    int numEvilNodes = 10;

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
      if ((i > 0) && (i < (numEvilNodes + 1))) {
        kadProt = ((KademliaProtocol) (Network.get(i).getProtocol(protocolEvilKadID)));
        kadProt.setProtocolID(protocolEvilKadID);
        node.setEvil(true);
      } else {
        kadProt = ((KademliaProtocol) (Network.get(i).getProtocol(protocolKadID)));
        kadProt.setProtocolID(protocolKadID);
      }

      generalNode.setKademliaProtocol(kadProt);
      kadProt.setNode(node);
      kadProt.setProtocolID(protocolKadID);
    }

    return false;
  }
}
