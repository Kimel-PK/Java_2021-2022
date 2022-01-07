import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PasswordComponents {
	public static final List<Character> lowercaseLetters = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

	public static final List<Character> uppercaseLetters = lowercaseLetters.stream().map(Character::toUpperCase)
			.collect(Collectors.toList());

	public static final List<Character> numbers = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

	public static final List<Character> symbols = List.of('.', ',', '@', '!', '&', '*', '+', '-', '_');

	public static final Map<Character, List<Character>> passwordComponents = Map.of('l', lowercaseLetters, 'u',
			uppercaseLetters, 'n', numbers, 's', symbols);

	public static final Set<Character> legalKeys = passwordComponents.keySet();

	public static List<Character> decodePasswordSchema(String schema) throws IllegalArgumentException {

		List<Character> result = schema.toLowerCase().chars().mapToObj(i -> Character.valueOf((char) i))
				.collect(Collectors.toList());

		if (illegalSchema(result))
			throw new IllegalArgumentException("Schemat hasła w postaci " + schema + " jest błędny");

		return result;
	}

	private static boolean illegalSchema(List<Character> schema) {
		return schema.stream().anyMatch(PasswordComponents::illegalCharacter);
	}

	private static boolean illegalCharacter(Character character2test) {
		return !legalKeys.contains(character2test);
	}

	public static void main(String[] args) {
		System.out.println(decodePasswordSchema("luns"));
		System.out.println(decodePasswordSchema("luNs"));
		System.out.println(decodePasswordSchema("lu Ns"));
//		symbols.add( 'x'); // ciekawostka !!!
	}
}
