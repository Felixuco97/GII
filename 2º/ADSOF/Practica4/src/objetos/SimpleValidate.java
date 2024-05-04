package objetos;

import Interfaces.*;

public class SimpleValidate implements IValidateMethod {

	@Override
	public boolean validate(IMiningMethod miningMethod, Block block) {
        // Recalcular el hash del bloque usando el método de minado proporcionado
        String newHash = miningMethod.createHash(block);
        
        // Comparar el hash recalculado con el hash original del bloque
        if (newHash.equals(block.getHash())) {
            return true;
        } else {
            return false;
        }
    }

}
