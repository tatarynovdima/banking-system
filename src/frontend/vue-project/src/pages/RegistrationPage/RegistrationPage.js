export default {
    name: 'RegistrationViews',
    data() {
        return {
            email: '',
            firstName: '',
            lastName: '',
            phoneNumber: '',
            selectedCountry: '',
            countries: [],
            password: '',
            confirmPassword: '',
            errorMsg: '',
            showPassword: false,
            showConfirmPassword: false
        }
    },
    computed: {
        passwordFieldType() {
            return this.showPassword ? 'text' : 'password';
        },
        passwordIconClass() {
            return this.showPassword ? 'fa-eye' : 'fa-eye-slash';
        },
    },
    methods: {
        fetchCountries() {
            fetch("http://localhost:8080/api/countries")
                .then(response => response.json())
                .then(data => {
                    this.countries = data;
                })
                .catch(error => {
                    console.error('Error fetching countries:', error);
                });
        },
        checkFields() {
            const noSpacesInPassword = !/\s/.test(this.password);
            return (
                this.email &&
                this.firstName &&
                this.lastName &&
                this.phoneNumber &&
                this.selectedCountry &&
                this.password &&
                this.confirmPassword &&
                noSpacesInPassword
            );
        },
        handleSubmit() {
            if (this.checkFields()) {
                const registrationData = {
                    email: this.email,
                    firstName: this.firstName,
                    lastName: this.lastName,
                    phoneNumber: this.phoneNumber,
                    password: this.password,
                    confirmPassword: this.confirmPassword,
                    countryName: this.selectedCountry,
                };

                fetch("http://localhost:8080/api/registration", {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(registrationData)
                })
                    .then(response => {
                        if (!response.ok) {
                            return response.json().then(data => {
                                throw new Error(data.message);
                            });
                        }
                        return response.json();
                    })
                    .then(() => {
                        this.$router.push('/email-confirmation');
                    })
                    .catch((error) => {
                        if (error.response && error.response.status === 400) {
                            this.errorMsg = error.response.data.message;
                        } else {
                            this.errorMsg = 'User with such email already exists';
                        }
                    });
            } else {
                this.errorMsg = 'Password cannot contain spaces';
            }
        },
        togglePasswordVisibility() {
            this.showPassword = !this.showPassword;
        }
    },
    mounted() {
        this.fetchCountries();

        const email = document.getElementById("email");
        email.addEventListener("input", (event) => {
            const emailRegex = /^[\w-\.]+@([a-zA-Z]+(\.[a-zA-Z]+)+)?$/;
            if (!emailRegex.test(email.value)) {
                if (!email.value.includes("@")) {
                    email.setCustomValidity("Please include the @ symbol in the email address");
                } else {
                    email.setCustomValidity("Please enter a valid email address");
                }
            } else {
                email.setCustomValidity("");
            }
        });

        const firstNameInput = document.getElementById("firstName");
        firstNameInput.addEventListener("input", (event) => {
            const startsWithCapitalLetter = /^[A-Z]/;
            const containsInvalidCharacters = /[^a-zA-Z]/;

            if (!startsWithCapitalLetter.test(firstNameInput.value)) {
                firstNameInput.setCustomValidity("First name must start with a capital letter");
            } else if (containsInvalidCharacters.test(firstNameInput.value)) {
                firstNameInput.setCustomValidity("First name must not contain numbers or special characters");
            } else {
                firstNameInput.setCustomValidity("");
            }
        });

        const lastNameInput = document.getElementById("lastName");
        lastNameInput.addEventListener("input", (event) => {
            const startsWithCapitalLetter = /^[A-Z]/;
            const containsInvalidCharacters = /[^a-zA-Z]/;

            if (!startsWithCapitalLetter.test(lastNameInput.value)) {
                lastNameInput.setCustomValidity("Last name must start with a capital letter");
            } else if (containsInvalidCharacters.test(lastNameInput.value)) {
                lastNameInput.setCustomValidity("Last name must not contain numbers or special characters");
            } else {
                lastNameInput.setCustomValidity("");
            }
        });

        const phoneNumberInput = document.getElementById("phoneNumber");
        phoneNumberInput.addEventListener("input", (event) => {
            const startsWithPlus = /^\+/;
            const notStartWithPlusZero = /^\+0/;
            const containsOnlyOnePlus = /^\+(?!.+\+)/;
            const isValidLength = /^.{0,16}$/;
            const containsOnlyDigits = /^[\d+]*$/;

            if (!startsWithPlus.test(phoneNumberInput.value)) {
                phoneNumberInput.setCustomValidity("Phone number must start with '+'");
            } else if (!containsOnlyOnePlus.test(phoneNumberInput.value)) {
                phoneNumberInput.setCustomValidity("Phone number must contain only one '+' symbol");
            } else if (notStartWithPlusZero.test(phoneNumberInput.value)) {
                phoneNumberInput.setCustomValidity("Phone number must not start with '+0'");
            } else if (!isValidLength.test(phoneNumberInput.value)) {
                phoneNumberInput.setCustomValidity("Phone number must be 16 characters or less");
            } else if (!containsOnlyDigits.test(phoneNumberInput.value)) {
                phoneNumberInput.setCustomValidity("Phone number must not contain letters or special characters");
            } else {
                phoneNumberInput.setCustomValidity("");
            }
        });

        const passwordInput = document.getElementById("password");
        const confirmPasswordInput = document.getElementById("confirmPassword");

        const validatePasswords = () => {
            const containsSpace = /\s/;
            const minLength = /^.{8,}$/;
            const maxLength = /^.{0,32}$/;
            const containsUpperCase = /[A-Z]/;
            const containsLowerCase = /[a-z]/;
            const containsDigit = /\d/;
            const containsSpecialChar = /[!@#$%^&*(),.?":{}|<>]/;

            if (!minLength.test(passwordInput.value)) {
                passwordInput.setCustomValidity("Password must be at least 8 characters long");
            } else if (!maxLength.test(passwordInput.value)) {
                passwordInput.setCustomValidity("Password must be at most 32 characters long");
            } else if (!containsUpperCase.test(passwordInput.value)) {
                passwordInput.setCustomValidity("Password must contain at least one uppercase letter");
            } else if (!containsLowerCase.test(passwordInput.value)) {
                passwordInput.setCustomValidity("Password must contain at least one lowercase letter");
            } else if (!containsDigit.test(passwordInput.value)) {
                passwordInput.setCustomValidity("Password must contain at least one number");
            } else if (!containsSpecialChar.test(passwordInput.value)) {
                passwordInput.setCustomValidity("Password must contain at least one special character");
            } else if (containsSpace.test(passwordInput.value)) {
                passwordInput.setCustomValidity("Password cannot contain spaces");
            } else {
                passwordInput.setCustomValidity("");
            }

            if (confirmPasswordInput.value !== passwordInput.value) {
                confirmPasswordInput.setCustomValidity("Passwords do not match");
            } else {
                confirmPasswordInput.setCustomValidity("");
            }
        };

        passwordInput.addEventListener("input", validatePasswords);
        confirmPasswordInput.addEventListener("input", validatePasswords);
    }
}
