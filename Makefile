build:
	@mvn clean install

validate-avram:
	@./avram-schemas/validate-schemas

shellerrors:
	@shellcheck -S error $$(for f in $$(git ls-files); do [ -x "$$f" ] && grep -q bash "$$f" || continue; echo $$f; done | grep -v catalogues/)

shellcheck:
	@shellcheck $$(for f in $$(git ls-files); do [ -x "$$f" ] && grep -q bash "$$f" || continue; echo $$f; done | grep -v catalogues/)
