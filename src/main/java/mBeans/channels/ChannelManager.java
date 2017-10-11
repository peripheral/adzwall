package mBeans.channels;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class ChannelManager {
	@PersistenceContext(unitName = "customerPU") // default type is PersistenceContextType.TRANSACTION
	EntityManager em;
	public Channel createCustomer(String name, String owner) {
		Channel channel = new Channel(name, owner);
		em.persist(channel);  // persist new Customer when JTA transaction completes (when method ends).
		// internally:
		//    1. Look for existing "customerPU" persistence context in active JTA transaction and use if found.
		//    2. Else create new "customerPU" persistence context (e.g. instance of org.hibernate.ejb.HibernatePersistence)
		//       and put in current active JTA transaction.
		return channel;       // return Customer entity (will be detached from the persistence context when caller gets control)
	}  // Transaction.commit will be called, Customer entity will be persisted to the database and "customerPU" persistence context closed
}
	