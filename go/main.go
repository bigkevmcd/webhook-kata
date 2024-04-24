package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/gregdel/pushover"
)

func main() {
	http.HandleFunc("/", hookHandler)
	fmt.Println("Listening at http://localhost:8080")
	log.Fatal(http.ListenAndServe(":8080", nil))
}

// hookHandler handles deployment_status notifications from GitHub and sends
// notifications to Pushover for distribution to devices.
func hookHandler(w http.ResponseWriter, r *http.Request) {
	githubEvent := r.Header.Get("X-GitHub-Event")
	if githubEvent != "deployment_status" {
		fmt.Fprintf(w, "ignored")
		return
	}

	decoder := json.NewDecoder(r.Body)
	var event deploymentStatusEvent
	if err := decoder.Decode(&event); err != nil {
		log.Printf("error decoding JSON: %v", err)
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	message := fmt.Sprintf("There has been a new deployment of %s to %s ref '%s' with state %s",
		event.Repository.Fullname,
		event.Deployment.Environment,
		event.Deployment.Ref,
		event.DeploymentStatus.State)
	title := fmt.Sprintf("Deployment of %s", event.Repository.Fullname)

	sendNotificationToPushover(title, message)
	fmt.Fprintf(w, message)

}

type deploymentStatusEvent struct {
	Repository struct {
		ID       int64  `json:"id"`
		Fullname string `json:"full_name"`
	} `json:"repository"`

	DeploymentStatus struct {
		State       string `json:"state"`
		TargetURL   string `json:"target_url"`
		Description string `json:"description"`
	} `json:"deployment_status"`

	Deployment struct {
		Ref         string `json:"ref"`
		Sha         string `json:"sha"`
		Task        string `json:"task"`
		Environment string `json:"environment"`
	} `json:"deployment"`
}

func sendNotificationToPushover(title, message string) error {
	app := pushover.New(os.Getenv("PUSHOVER_API_TOKEN"))
	recipient := pushover.NewRecipient(os.Getenv("PUSHOVER_API_USER"))
	msg := pushover.NewMessageWithTitle(message, title)

	_, err := app.SendMessage(msg, recipient)
	if err != nil {
		return err
	}

	return nil
}
