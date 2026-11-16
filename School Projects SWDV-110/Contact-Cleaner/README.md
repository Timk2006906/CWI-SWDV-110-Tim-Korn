## README Reflection

**1. Strings are immutable. Name a place in your code where forgetting that would have caused a bug.**

Strings can't be changed after they're created. In my `normalizeName()` method, I used `String name = raw.trim()` to save the name without extra spaces. If I only used `raw.trim()` without saving it, the spaces would still be there.

**2. Why does `split("|")` behave differently from `split("\\|")`?**

The `|` character has a special meaning in regular expressions. Using `split("|")` doesn't separate the contact information the way I want. Using `split("\\|")` tells Java to split the line at the actual pipe character.

**3. Where did you choose StringBuilder instead of +, and why?**

I used `StringBuilder` in my `ContactReport` class to put the report together. It lets me add more text using `append()` without creating a new String every time. This is useful when building a report with multiple contacts.

**4. Maven test results**

I ran `mvn clean test` and all 20 tests passed.

- Tests run: 20
- Failures: 0
- Errors: 0
- Skipped: 0
- BUILD SUCCESS