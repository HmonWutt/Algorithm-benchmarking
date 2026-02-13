.PHONY: venv install clean help

# Python interpreter to use
PYTHON := python3

# Virtual environment directory
VENV := venv

# Path to Python in venv
VENV_PYTHON := $(VENV)/bin/python
VENV_PIP := $(VENV)/bin/pip

help:
	@echo "Available targets:"
	@echo "  make venv       - Create virtual environment"
	@echo "  make install    - Install dependencies from requirements.txt"
	@echo "  make all        - Create venv and install dependencies"
	@echo "  make clean      - Remove virtual environment"
	@echo ""
	@echo "After running 'make venv' or 'make all', activate with:"
	@echo "  source venv/bin/activate"

# Create virtual environment
venv:
	@echo "Creating virtual environment..."
	$(PYTHON) -m venv $(VENV)
	@echo "Virtual environment created in $(VENV)/"
	@echo "Activate it with: source $(VENV)/bin/activate"

# Install dependencies
install: venv
	@echo "Installing dependencies..."
	$(VENV_PIP) install --upgrade pip
	@if [ -f requirements.txt ]; then \
		$(VENV_PIP) install -r requirements.txt; \
		echo "Dependencies installed from requirements.txt"; \
	else \
		echo "No requirements.txt found"; \
	fi

# Create venv and install dependencies
all: install

# Remove virtual environment
clean:
	@echo "Removing virtual environment..."
	rm -rf $(VENV)
	@echo "Virtual environment removed"
